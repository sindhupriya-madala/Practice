package com.manoj.transformersae.ui

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.design.widget.Snackbar
import com.manoj.transformersae.R

import com.manoj.transformersae.model.BotModel
import com.manoj.transformersae.ui.detailview.ItemDetailActivity
import kotlinx.android.synthetic.main.activity_item_list.*
import kotlinx.android.synthetic.main.item_list.*

/**
 * An activity representing a list of Pings. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a [ItemDetailActivity] representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
class MainActivity : AppCompatActivity() {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private var twoPane: Boolean = false
    private lateinit var mainViewModel: MainViewModel;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_list)

        setSupportActionBar(toolbar)
        toolbar.title = title

        fab.setOnClickListener { view ->
            goToCreateView()
        }

        if (item_detail_container != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            twoPane = true
        }
        mainViewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        mainViewModel.init(applicationContext)

        mainViewModel.listLiveData.observe(this, Observer { listen(it!!) })

    }

    private fun listen(list: List<BotModel>) {
        if (list != null && list.isNotEmpty()) {
            //TODO : DISPLAY EXISTING TRANSFORMERS LIST FRAGMENT
        } else {
            //TODO : SHOW ADD FRAGMENT TO ADD A NEW TRANSFORMER FRAGMENT
            goToCreateView()
        }
    }

    private fun goToCreateView() {
        val intent = Intent(this, ItemDetailActivity::class.java)
        startActivity(intent)
    }
}
