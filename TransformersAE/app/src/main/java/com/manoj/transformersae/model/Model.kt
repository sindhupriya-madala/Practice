package com.manoj.transformersae.model

import android.arch.lifecycle.MutableLiveData
import android.content.Context
import com.manoj.transformersae.service.AppDBService
import com.vemuru.transformers.service.HTTPService
import kotlinx.coroutines.experimental.launch

/**
 * Created by Manoj Vemuru on 2018-09-19.
 */
class Model private constructor(context: Context){

    var allBots:MutableLiveData<List<BotModel>> = MutableLiveData()

    companion object {
        private var INSTANCE:Model? = null
        val mCreateRequestSuccess:MutableLiveData<Boolean>  = MutableLiveData()
        fun getInstance(context: Context):Model {
                if (INSTANCE == null) {
                    INSTANCE = Model(context)
                }
            return INSTANCE as Model;
        }
    }

    private var DB: AppDBService = AppDBService.getAppDatabase(context)

    fun insertTransformerDB(botModel: BotModel) {
        launch {
            DB.botDao().insertBot(botModel)
        }
    }

    fun getAllTransformersDB() {
        launch {
            allBots.postValue(DB.botDao().getAll());
        }

    }

    fun updateTransformerDB(botModel: BotModel) {
        launch {
            DB.botDao().updateBot(botModel)
        }
    }

    fun deleteTransformerDB(botModel: BotModel) {
        launch {
            DB.botDao().deleteBot(botModel)
        }
    }

    fun saveTransformer(botModel: BotModel, token:String, context: Context) {
        HTTPService.getInstance().requestCreateTransformer(botModel, token, context)
    }
}