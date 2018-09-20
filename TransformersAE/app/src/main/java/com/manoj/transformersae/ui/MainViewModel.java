package com.manoj.transformersae.ui;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;

import com.manoj.transformersae.model.BotModel;
import com.manoj.transformersae.model.Model;

import java.util.List;

/**
 * Created by Manoj Vemuru on 2018-09-19.
 */
public class MainViewModel extends ViewModel {

    private LiveData<List<BotModel>> listLiveData;
    private Context mContext;

    public void init(Context context) {
        this.mContext = context;
        Model.Companion.getInstance(context).getAllTransformers();
    }

    public LiveData<List<BotModel>> getListLiveData() {
        return Model.Companion.getInstance(mContext).getAllBots();
    }
}
