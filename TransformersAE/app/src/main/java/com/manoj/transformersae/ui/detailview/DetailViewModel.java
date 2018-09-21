package com.manoj.transformersae.ui.detailview;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;

import com.manoj.transformersae.model.BotModel;
import com.manoj.transformersae.model.Model;
import com.manoj.transformersae.util.AppUtill;

/**
 * Created by Manoj Vemuru on 2018-09-20.
 */
public class DetailViewModel extends ViewModel {
    private MutableLiveData<BotModel> mBotModel;
    private Context context;

    public void init(Context context) {
        this.context = context;
    }

    public MutableLiveData<BotModel> getBotModel() {
        return mBotModel;
    }

    public void save(BotModel botModel) {
        Model.Companion.getInstance(context).saveTransformer(botModel, AppUtill.getSavedToken(context), context);
    }

    public LiveData<Boolean> getSaveSuccess() {
        return Model.Companion.getMCreateRequestSuccess();
    }
}
