package com.manoj.transformersae.ui;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;

import com.manoj.transformersae.model.BotModel;
import com.manoj.transformersae.model.Model;
import io.reactivex.subjects.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Manoj Vemuru on 2018-09-19.
 */
public class MainViewModel extends ViewModel {

    private Context mContext;
    private List<BotModel> mAllBots = new ArrayList<>();
    public void init(Context context) {
        this.mContext = context;
    }

    public void requestToRefreshList() {
        Model.Companion.getInstance(mContext).getAllTransformersDB();
    }

    public LiveData<List<BotModel>> getListLiveData() {
        return Model.Companion.getInstance(mContext).getMLiveData();
    }

    public List<BotModel> getAllBots() {
        return mAllBots;
    }

    public void setAllBots(List<BotModel> mAllBots) {
        this.mAllBots = mAllBots;
    }

    public BotModel getBotById(String id) {
        for (BotModel botModel : mAllBots) {
            if(botModel.getId().equals(id)) {
                return botModel;
            }
        }
        return null;
    }

    public PublishSubject<Boolean> refreshSubject() {
        return Model.Companion.getInstance(mContext).getMCreateRequestSuccess();
    }

    public void startWar(){
        Model.Companion.getInstance(mContext).launchWar();
    }

    public  PublishSubject<String> warResponse() {
        return Model.Companion.getInstance(mContext).getMWarRequestResponse();
    }
}
