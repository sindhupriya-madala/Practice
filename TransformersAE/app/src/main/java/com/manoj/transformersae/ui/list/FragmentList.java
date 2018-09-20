package com.manoj.transformersae.ui.list;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.manoj.transformersae.R;

/**
 * Created by Manoj Vemuru on 2018-09-19.
 */
public class FragmentList extends Fragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.item_detail, container, false);

        return rootView;
    }

    private void setupRecyclerView(RecyclerView recyclerView) {
//        recyclerView.adapter = Adapter(this, DummyContent.ITEMS, twoPane)
    }
}
