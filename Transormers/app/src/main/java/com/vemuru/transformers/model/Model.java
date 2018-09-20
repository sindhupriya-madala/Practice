package com.vemuru.transformers.model;

import android.content.Context;

import com.vemuru.transformers.service.AppDBService;

import java.util.List;

/**
 * Created by Manoj Vemuru on 2018-09-19.
 */
public class Model {
    private AppDBService DB;

    public Model(Context context) {
        DB = AppDBService.getAppDatabase(context);
    }

    public void insertTransformer(BotModel botModel) {
        DB.botDao().insertBot(botModel);
    }

    public List<BotModel> getAllTransformers() {
        return DB.botDao().getAll();
    }

    public void updateTransformer(BotModel botModel) {
        DB.botDao().updateBot(botModel);
    }

    public void deleteTransformer(BotModel botModel) {
        DB.botDao().deleteBot(botModel);
    }
}
