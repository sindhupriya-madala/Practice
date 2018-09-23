package com.manoj.transformersae.ui

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import com.manoj.transformersae.R
import com.manoj.transformersae.model.BotModel
import com.manoj.transformersae.model.Model
import com.manoj.transformersae.ui.detailview.ItemDetailActivity
import com.manoj.transformersae.ui.list.FragmentList
import com.manoj.transformersae.util.AppUtill
import com.manoj.transformersae.util.AppUtill.VIEW_TYPE_KEY
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_item_list.*
import kotlinx.android.synthetic.main.item_list.*
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

/**
 * An activity representing a list of Pings. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a [ItemDetailActivity] representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
class MainActivity : AppCompatActivity(), View.OnClickListener {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private var twoPane: Boolean = false
    private lateinit var mMainViewModel: MainViewModel
    private lateinit var mFragmentList: FragmentList
    private lateinit var mDisposable:Disposable
    private lateinit var mwarDisposable:Disposable
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_list)
        setSupportActionBar(toolbar)
        toolbar.title = title

        fab.setOnClickListener { _->
            goToCreateView()
        }



        if (item_detail_container != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            twoPane = true
        }
        setUpListFragment()

        mMainViewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        mMainViewModel.init(applicationContext)

        mMainViewModel.listLiveData.observe(this, Observer { listen(it!!) })

        mDisposable = mMainViewModel.refreshSubject().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe (
                        { _it -> processResult(_it) },
                        { _error ->
                            Log.e("ERROR", _error.localizedMessage)
                        })
        mwarDisposable = mMainViewModel.warResponse().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe (
                        { _it ->  showWarResult(_it)},
                        { _error ->
                            Log.e("ERROR", _error.localizedMessage)
                        })
        war_button.setOnClickListener {
            mMainViewModel.startWar();
        }
    }

    override fun onResume() {
        super.onResume()
        mMainViewModel.requestToRefreshList();
    }

    override fun onDestroy() {
        super.onDestroy()
        mDisposable.dispose()
        mwarDisposable.dispose()
    }

    private fun listen(list: List<BotModel>) {
        if (list.isNotEmpty()) {
            mMainViewModel.allBots = list
            mFragmentList.updateAllItems(list)
        } else {
            goToCreateView()
        }
    }

    private fun goToCreateView() {
        val intent = Intent(this, ItemDetailActivity::class.java)
        intent.putExtra(VIEW_TYPE_KEY, AppUtill.TYPE.CREATE.value)
        startActivity(intent)
    }

    private fun processResult(result: Boolean) {
        if(result)
            mMainViewModel.requestToRefreshList()
    }

    private fun setUpListFragment() {
        mFragmentList = FragmentList()
        supportFragmentManager.beginTransaction()
                .add(R.id.frameLayout, mFragmentList)
                .commit()
    }

    override fun onClick(view: View?) {
        val id = view?.getTag(R.id.item_list_layout) as String
        val botModel = mMainViewModel.getBotById(id)
        when(view.id) {
            R.id.item_list_layout -> {
                goToDetail(AppUtill.TYPE.VIEW.value, botModel)
            }
            R.id.edit -> {
                goToDetail(AppUtill.TYPE.UPDATE.value, botModel)
            }
            R.id.delete -> {
                Model.getInstance(applicationContext).requestDeleteTransformer(botModel, applicationContext)
            }
        }
    }

    private fun goToDetail(viewType:Int, botModel: BotModel) {
        val intent = Intent(this, ItemDetailActivity::class.java)
        intent.putExtra(AppUtill.BOT_MODEL_KEY, botModel)
        intent.putExtra(VIEW_TYPE_KEY, viewType)
        startActivity(intent)
    }

    private fun showWarResult(result: String) {
        val alertDailog: AlertDialog.Builder = AlertDialog.Builder(this)
        alertDailog.setTitle("War Result")
        alertDailog.setMessage(result)
        alertDailog.create()
        alertDailog.show()

    }
}
