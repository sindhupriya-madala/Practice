package com.vemuru.presto.flickrgallery.ui.ItemList;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.vemuru.presto.flickrgallery.model.PhotoModel;
import com.vemuru.presto.flickrgallery.service.FlickServiceModel;

import java.util.List;

/**
 * Created by Manoj Vemuru on 2018-09-18.
 */
public class ItemListViewModel extends ViewModel{

    private FlickServiceModel flickServiceModel;
    private int mPageCount = 0;

    public void init(int page) {
        flickServiceModel = FlickServiceModel.Companion.getINSTANCE();
        flickServiceModel.loadInitialPhotos(page);
    }

    public LiveData<List<PhotoModel>> getImageList() {
        return flickServiceModel.getListPhotos();
    }

    public int getmPageCount() {
        return mPageCount;
    }
}
