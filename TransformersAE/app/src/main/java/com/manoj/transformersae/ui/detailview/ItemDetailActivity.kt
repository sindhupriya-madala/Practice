package com.manoj.transformersae.ui.detailview

import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.Toast
import com.manoj.transformersae.R
import com.manoj.transformersae.model.BotModel
import com.manoj.transformersae.ui.MainActivity
import com.manoj.transformersae.util.AppUtill
import com.manoj.transformersae.util.AppUtill.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_item_detail.*

/**
 * An activity representing a single Item detail screen. This
 * activity is only used on narrow width devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a [MainActivity].
 */
class ItemDetailActivity : AppCompatActivity() {
    private lateinit var mRadioButtonGroup:RadioGroup
    private lateinit var mDetailViewModel: DetailViewModel
    private lateinit var mDetailFragment: ItemDetailFragment
    private lateinit var mNameEditText:EditText
    private lateinit var mSaveButton: Button

    private var mViewType:Int = 0
    private lateinit var mDisposable: Disposable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_detail)
        setSupportActionBar(detail_toolbar)

        var botModel: BotModel? = null
        if(intent.extras != null) {
            val bundle: Bundle = intent?.extras!!
            bundle.let {
                if (it.containsKey(BOT_MODEL_KEY)) {
                    botModel = it.getParcelable(BOT_MODEL_KEY)
                }
                if (it.containsKey(VIEW_TYPE_KEY)) {
                    mViewType = it.getInt(VIEW_TYPE_KEY, 0)
                }
            }
        }

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own detail action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }

        // Show the Up button in the action bar.
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Create the detail fragment and add it to the activity
        // using a fragment transaction.
        mDetailFragment = ItemDetailFragment().apply {
            arguments = Bundle().apply {
                putInt(VIEW_TYPE_KEY, mViewType)
                if(botModel != null)
                    putParcelable(BOT_MODEL_KEY, botModel)
            }
        }

        mNameEditText = findViewById(R.id.name_edit_text)
        mSaveButton = findViewById(R.id.button_save)
        mRadioButtonGroup = findViewById(R.id.radio_group)

        mDetailViewModel = ViewModelProviders.of(this).get(DetailViewModel::class.java)
        mDetailViewModel.init(applicationContext)

        if(mViewType == AppUtill.TYPE.VIEW.value || mViewType == AppUtill.TYPE.UPDATE.value) {
            mDetailViewModel.setBotModel(botModel!!)
        }

        supportFragmentManager.beginTransaction()
                .add(R.id.item_detail_container, mDetailFragment)
                .commit()

        mSaveButton.setOnClickListener { _-> saveTransformer()}

        setViewType()

        mDisposable = mDetailViewModel.getSuccessSubject()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe (
                        { _it -> processResult(_it) },
                        { _error ->
                            Log.e("ERROR", _error.localizedMessage)
                        })
    }

    private fun setViewType() {
        when(mViewType) {
            AppUtill.TYPE.VIEW.value -> {
                bindImage(mDetailViewModel.getBotModel().teamIcon, fab, false)
                toolbar_layout.title = mDetailViewModel.getBotModel().name
                setupCreateViews(true)
            }
            AppUtill.TYPE.CREATE.value -> {
                mSaveButton.text = "Create"
                setupCreateViews(false)
            }
            AppUtill.TYPE.UPDATE.value -> {
                mNameEditText.setText(mDetailViewModel.getBotModel().name)
                mNameEditText.hint = ""
                mSaveButton.text = "Update"
                when(mDetailViewModel.getBotModel().team) {
                    AppUtill.TEAM_A_KEY -> {
                        radio_a.isSelected = true
                        radio_d.isSelected = false
                        mRadioButtonGroup.invalidate()
                    }
                    AppUtill.TEAM_D_KEY -> {
                        radio_a.isSelected = false
                        radio_d.isSelected = true
                        mRadioButtonGroup.invalidate()
                    }
                }
                setupCreateViews(false)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mDisposable.dispose()
    }

    private fun setupCreateViews(isVisible:Boolean) {
        var visibility = when(isVisible) {
            true -> View.VISIBLE
            false -> View.GONE
        }

        app_bar.visibility = visibility
        fab.visibility = visibility

        visibility = when(!isVisible) {
            true -> View.VISIBLE
            false -> View.GONE
        }
        button_save.visibility = visibility
        create_layout.visibility = visibility
        name_edit_text_layout.visibility = visibility
    }

    override fun onOptionsItemSelected(item: MenuItem) =
            when (item.itemId) {
                android.R.id.home -> {
                    // This ID represents the Home or Up button. In the case of this
                    // activity, the Up button is shown. For
                    // more details, see the Navigation pattern on Android Design:
                    //
                    // http://developer.android.com/design/patterns/navigation.html#up-vs-back

                    navigateUpTo(Intent(this, MainActivity::class.java))
                    true
                }
                else -> super.onOptionsItemSelected(item)
            }

    private fun getSelectedTeam() : String {
        return when(mRadioButtonGroup.checkedRadioButtonId) {
            R.id.radio_a -> "A"
            R.id.radio_d -> "D"
            else -> "D"
        }
    }

    private fun saveTransformer() {
        when(mViewType) {
            AppUtill.TYPE.VIEW.value -> {
                mSaveButton.text = "View"
                mSaveButton.isClickable = false
                mSaveButton.isEnabled = false
            }
            AppUtill.TYPE.CREATE.value -> {
                mSaveButton.isClickable = true
                mSaveButton.isEnabled = true
                val botModel = mDetailFragment.getBotModel(BotModel())
                botModel.name = mNameEditText.text.toString()
                botModel.team = getSelectedTeam()
                mDetailViewModel.save(botModel)
            }
            AppUtill.TYPE.UPDATE.value -> {
                mSaveButton.isClickable = true
                mSaveButton.isEnabled = true
                val botModel = mDetailFragment.getBotModel(mDetailViewModel.getBotModel())
                botModel.name = mNameEditText.text.toString()
                botModel.team = getSelectedTeam()
                mDetailViewModel.update(botModel)
            }
        }
    }

    private fun processResult(result: Boolean) {
        if (result) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        } else {
            Toast.makeText(this, "Something went wrong.\n Please try again.", Toast.LENGTH_SHORT).show()
        }
    }
}
