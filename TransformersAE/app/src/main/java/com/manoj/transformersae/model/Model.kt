package com.manoj.transformersae.model

import android.arch.lifecycle.MutableLiveData
import android.content.Context
import com.manoj.transformersae.service.AppDBService
import kotlinx.coroutines.experimental.launch

/**
 * Created by Manoj Vemuru on 2018-09-19.
 */
class Model private constructor(context: Context){

    var allBots:MutableLiveData<List<BotModel>> = MutableLiveData()

    companion object {
        private var INSTANCE:Model? = null

        fun getInstance(context: Context):Model {
                if (INSTANCE == null) {
                    INSTANCE = Model(context)
                }
            return INSTANCE as Model;
        }
    }

    private var DB: AppDBService = AppDBService.getAppDatabase(context)

    fun insertTransformer(botModel: BotModel) {
        launch {
            DB.botDao().insertBot(botModel)
        }
    }

    fun getAllTransformers() {
        launch {
            allBots.postValue(DB.botDao().getAll());
        }

    }

    fun updateTransformer(botModel: BotModel) {
        launch {
            DB.botDao().updateBot(botModel)
        }
    }

    fun deleteTransformer(botModel: BotModel) {
        launch {
            DB.botDao().deleteBot(botModel)
        }
    }

}