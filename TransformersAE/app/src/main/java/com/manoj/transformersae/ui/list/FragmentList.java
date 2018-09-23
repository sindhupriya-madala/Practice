package com.manoj.transformersae.ui.list;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.manoj.transformersae.R;
import com.manoj.transformersae.model.BotModel;
import com.manoj.transformersae.ui.MainActivity;
import com.manoj.transformersae.ui.adapter.Adapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Manoj Vemuru on 2018-09-19.
 */
public class FragmentList extends Fragment {

    private Adapter mAdapter;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.list_fragment, container, false);
        RecyclerView mRecyclerView = rootView.findViewById(R.id.bot_list);
        // Create the grid layout manager with 2 columns.
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        mRecyclerView.setLayoutManager(gridLayoutManager);
        mAdapter = new Adapter((MainActivity) getActivity(), new ArrayList<BotModel>());
        mRecyclerView.setAdapter(mAdapter);
        return rootView;
    }

    public void updateAllItems(List<BotModel> botModelList) {
        mAdapter.updateList(botModelList);
    }
}
