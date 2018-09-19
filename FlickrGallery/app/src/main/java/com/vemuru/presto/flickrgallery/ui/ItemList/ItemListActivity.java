package com.vemuru.presto.flickrgallery.ui.ItemList;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.vemuru.presto.flickrgallery.R;
import com.vemuru.presto.flickrgallery.adapter.SimpleItemRecyclerViewAdapter;
import com.vemuru.presto.flickrgallery.model.PhotoModel;

import java.util.List;

/**
 * An activity representing a list of Items. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched
 * item details. On tablets, the activity presents the list of items and
 */
public class ItemListActivity extends AppCompatActivity {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private ItemListViewModel itemListViewModel;
    private RecyclerView mRecyclerView;
    private SimpleItemRecyclerViewAdapter mSimpleItemRecyclerViewAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        mRecyclerView = findViewById(R.id.item_list);
        assert mRecyclerView != null;
        setupRecyclerView((RecyclerView) mRecyclerView);

        itemListViewModel = ViewModelProviders.of(this).get(ItemListViewModel.class);
        itemListViewModel.init(itemListViewModel.getmPageCount());
        setupEndListner();
    }

    private void setupEndListner() {
        itemListViewModel.getImageList().observe(this, this::updateItems);
    }

    private void updateItems(List<PhotoModel> items) {
        mSimpleItemRecyclerViewAdapter.updateList(items);
        mSimpleItemRecyclerViewAdapter.notifyDataSetChanged();
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        mSimpleItemRecyclerViewAdapter = new SimpleItemRecyclerViewAdapter(this);
        recyclerView.setAdapter(mSimpleItemRecyclerViewAdapter);
        // Create the grid layout manager with 2 columns.
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        // Set layout manager.
        recyclerView.setLayoutManager(gridLayoutManager);
    }
}
