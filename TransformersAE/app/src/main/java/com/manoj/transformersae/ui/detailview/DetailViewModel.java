package com.manoj.transformersae.ui.detailview;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;

import com.manoj.transformersae.model.BotModel;

/**
 * Created by Manoj Vemuru on 2018-09-20.
 */
public class DetailViewModel extends ViewModel {
    private MutableLiveData<BotModel> mBotModel;

    public void init(Context context) {

    }

    public MutableLiveData<BotModel> getBotModel() {
        return mBotModel;
    }
}
