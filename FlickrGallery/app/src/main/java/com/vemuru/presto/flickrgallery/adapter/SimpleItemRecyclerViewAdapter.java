package com.vemuru.presto.flickrgallery.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.vemuru.presto.flickrgallery.R;
import com.vemuru.presto.flickrgallery.model.PhotoModel;
import com.vemuru.presto.flickrgallery.ui.ItemList.ItemListActivity;
import com.vemuru.presto.flickrgallery.util.AppUtill;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Manoj Vemuru on 2018-09-18.
 */
public class SimpleItemRecyclerViewAdapter extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

    private final ItemListActivity mParentActivity;
    private List<PhotoModel> mValues = new ArrayList<>();

    public SimpleItemRecyclerViewAdapter(ItemListActivity parent) {
        mParentActivity = parent;
    }

    public void updateList(List<PhotoModel> items) {
        mValues.addAll(items);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_card_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mTitleTextView.setText("Title : " +mValues.get(position).title);
        AppUtill.bindImage(mValues.get(position).getImageUrl(), holder.mImageView, holder.mDimensionsTextView,false);
        holder.itemView.setTag(mValues.get(position));
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        final TextView mTitleTextView;
        final TextView mDimensionsTextView;
        final ImageView mImageView;
        ViewHolder(View view) {
            super(view);
            mTitleTextView = (TextView) view.findViewById(R.id.image_title_text);
            mDimensionsTextView = (TextView) view.findViewById(R.id.image_dimensions_text);
            mImageView = (ImageView) view.findViewById(R.id.image);
        }
    }
}
