package com.manoj.transformersae.ui.detailview

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.RadioGroup
import com.manoj.transformersae.R
import com.manoj.transformersae.model.BotModel
import com.manoj.transformersae.ui.MainActivity
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_detail)
        setSupportActionBar(detail_toolbar)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own detail action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }

        // Show the Up button in the action bar.
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // savedInstanceState is non-null when there is fragment state
        // saved from previous configurations of this activity
        // (e.g. when rotating the screen from portrait to landscape).
        // In this case, the fragment will automatically be re-added
        // to its container so we don't need to manually add it.
        // For more information, see the Fragments API guide at:
        //
        // http://developer.android.com/guide/components/fragments.html
        //
        if (savedInstanceState == null) {
            // Create the detail fragment and add it to the activity
            // using a fragment transaction.
            mDetailFragment = ItemDetailFragment().apply {
                arguments = Bundle().apply {
                    putString(ItemDetailFragment.ARG_ITEM_ID,
                            intent.getStringExtra(ItemDetailFragment.ARG_ITEM_ID))
                }
            }

            supportFragmentManager.beginTransaction()
                    .add(R.id.item_detail_container, mDetailFragment)
                    .commit()
        }
        mNameEditText = findViewById(R.id.name_edit_text)
        mSaveButton = findViewById(R.id.button_save)
        mRadioButtonGroup = findViewById(R.id.radio_group)

        mDetailViewModel = ViewModelProviders.of(this).get(DetailViewModel::class.java)
        mDetailViewModel.init(applicationContext)

        mSaveButton.setOnClickListener { _-> saveTransformer()}

        mDetailViewModel.saveSuccess.observe(this, Observer {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        })
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
            else -> "A"
        }
    }

    private fun saveTransformer() {
        var botModel = mDetailFragment.getBotModel()
        botModel.name = mNameEditText.text.toString()
        botModel.team = getSelectedTeam()
        mDetailViewModel.save(botModel)
    }


}
