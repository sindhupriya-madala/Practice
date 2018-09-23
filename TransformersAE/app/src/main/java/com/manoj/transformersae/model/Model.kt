package com.manoj.transformersae.model

import android.arch.lifecycle.MutableLiveData
import android.content.Context
import android.support.annotation.VisibleForTesting
import com.manoj.transformersae.service.AppDBService
import com.manoj.transformersae.util.AppUtill
import com.vemuru.transformers.service.HTTPService
import io.reactivex.subjects.PublishSubject
import kotlinx.coroutines.experimental.launch

/**
 * Created by Manoj Vemuru on 2018-09-19.
 */
open class Model {

    protected lateinit var DB: AppDBService

    private lateinit var mContext: Context
    var mLiveData:MutableLiveData<List<BotModel>> = MutableLiveData()

    val mCreateRequestSuccess = PublishSubject.create<Boolean>()
    val mWarRequestResponse = PublishSubject.create<String>()

    companion object {
        private var INSTANCE:Model? = null
        fun getInstance(context: Context):Model {
                if (INSTANCE == null) {
                    INSTANCE = Model(context)
                }
            return INSTANCE as Model;
        }
    }

    private constructor(context: Context) {
        isForTesting = false
        mContext = context
        DB = AppDBService.getAppDatabase(mContext)
    }

    @VisibleForTesting
    private var isForTesting:Boolean = false
    @VisibleForTesting
    constructor(context: Context, boolean: Boolean) {
        isForTesting = boolean
        mContext = context
        DB = AppDBService.getAppDatabase(mContext)
    }


    fun getAllTransformersDB() {
        launch {
            val all = DB.botDao().all
            mLiveData.postValue(all)
        }

    }

    fun insertTransformersDB(botModelList: List<BotModel>) {
        DB.botDao().insertAllBot(botModelList)
    }

    fun insertTransformerDB(botModel: BotModel) {
            DB.botDao().insertBot(botModel)
    }

    fun updateTransformerDB(botModel: BotModel) {
            DB.botDao().updateBot(botModel)
    }

    fun deleteTransformerDB(botModel: BotModel) {
        launch {
            DB.botDao().deleteBot(botModel)
        }
    }

    fun saveTransformer(botModel: BotModel, token:String, context: Context) {
        launch {
            val result = HTTPService.getInstance().requestCreateTransformer(botModel, token, context)
            mCreateRequestSuccess.onNext(result)
        }
    }

    fun updateTransformer(botModel: BotModel, token:String, context: Context) {
        launch {
            val result = HTTPService.getInstance().requestUpdateTransformer(botModel, token, context)
            mCreateRequestSuccess.onNext(result)
        }
    }

    fun requestDeleteTransformer(botModel: BotModel, context: Context) {
        launch {
            val result = HTTPService.getInstance().requestDeleteTransformer(botModel, AppUtill.getSavedToken(context), context)
            mCreateRequestSuccess.onNext(result)
        }
    }

    fun launchWar() {
        launch {
            val list = DB.botDao().all
            val autoBotList:ArrayList<BotModel> = ArrayList()
            val deciptionBotList:ArrayList<BotModel> = ArrayList()

            for(botModel : BotModel in list) {
                if (botModel.team == AppUtill.TEAM_A_KEY)
                    autoBotList.add(botModel)
                else
                    deciptionBotList.add(botModel)
            }

            autoBotList.sort()
            deciptionBotList.sort()
            var countAutoBotWinner:Int = 0;
            var countDecipticon:Int = 0;
            for (autoBot :BotModel in autoBotList) {
                if(autoBot.name.contains("Optimus Prime") || autoBot.name.contains("Predaking")) {
                    countAutoBotWinner++
                }
            }
            for (decipticon :BotModel in deciptionBotList) {
                if(decipticon.name.contains("Optimus Prime") || decipticon.name.contains("Predaking")) {
                    countDecipticon++
                }
            }

            if((countAutoBotWinner +  countDecipticon) > 1){
                mWarRequestResponse.onNext("all competitors destroyed")
            } else if(countAutoBotWinner > 0) {
                mWarRequestResponse.onNext("Winning Team (Autobots)")
            } else if(countDecipticon > 0) {
                mWarRequestResponse.onNext("Winning Team (Decipticons)")
            } else {
                mWarRequestResponse.onNext(processWar(autoBotList, deciptionBotList))
            }

        }
    }

    private fun processWar(listAutoBot : ArrayList<BotModel>, listDecipticons : ArrayList<BotModel>) : String {
        var autoBotCount:ArrayList<String> = ArrayList()
        var decipticonCount:ArrayList<String> = ArrayList()

        var count:Int = when(listAutoBot.size >= listDecipticons.size) {
            true ->   listDecipticons.size -1
            false-> listAutoBot.size-1
        }
        for(i in 0..count) {
                //check for courage and strength difference
                if(listAutoBot[i].courage > listDecipticons[i].courage
                        && listAutoBot[i].strength > listDecipticons[i].strength
                        && checkCourageAndStrength(listAutoBot[i], listDecipticons[i])) {
                    autoBotCount.add(listAutoBot[i].name)
                } else if(listDecipticons[i].courage > listAutoBot[i].courage
                        && listDecipticons[i].strength > listAutoBot[i].strength
                        && checkCourageAndStrength(listDecipticons[i], listAutoBot[i])) {
                    decipticonCount.add(listDecipticons[i].name)
                } else if (listAutoBot[i].skill - listDecipticons[i].skill >= 3) {
                    autoBotCount.add(listAutoBot[i].name)
                } else if(listDecipticons[i].skill - listAutoBot[i].skill >= 3) {
                    decipticonCount.add(listDecipticons[i].name)
                } else if(listAutoBot[i].overAllRating > listDecipticons[i].overAllRating) {
                    autoBotCount.add(listAutoBot[i].name)
                } else if(listDecipticons[i].overAllRating > listAutoBot[i].overAllRating) {
                    decipticonCount.add(listDecipticons[i].name)
                }
        }

        var winningTeam = ""
        winningTeam  = when(count > 0) {
            true-> """${count+1} Battles"""
            false ->"""${count+1} Battle"""
        }
        winningTeam += "\n"
        if(autoBotCount.size > decipticonCount.size) {
            //TODO: autobots won
            winningTeam += "Winning Team (Autobots): " + getWinners(autoBotCount) + "\n"
            if(listDecipticons.size > listAutoBot.size) {
                winningTeam += "Survivors from the losing team (Decepticons): " +getSurvivorsNames(listDecipticons, listAutoBot.size) + "\n"
            }

        } else if (decipticonCount.size > autoBotCount.size) {
            //TODO: Decipticons won
            winningTeam = winningTeam + "Winning Team (Decepticons): " + getWinners(decipticonCount) + "\n"
            if(listAutoBot.size > listDecipticons.size) {
                winningTeam = winningTeam + "Survivors from the losing team (Autobots): " +getSurvivorsNames(listAutoBot, listDecipticons.size) + "\n"
            }
        } else {
            //TODO: all transformers in battle destroyed
            winningTeam ="all transformers in battle destroyed"
            return winningTeam
        }

        return winningTeam
    }

    private fun checkCourageAndStrength(botModel1: BotModel, botModel: BotModel) : Boolean {
        return when(botModel1.courage - botModel.courage >= 4 && botModel1.strength - botModel.strength >=3){
            true -> true
            false -> false
        }
    }

    private fun getWinners(listNames:ArrayList<String>) : String {
        var winnersNames = listNames[0]

        if(listNames.size > 1) {
            for (i in 1 until listNames.size) {
                val name = listNames[i]
                winnersNames = ", $name"
            }
        }
        return winnersNames
    }

    private fun getSurvivorsNames(listAutoBot: ArrayList<BotModel>, startPos:Int) : String {
        var survivorsNames = listAutoBot[startPos].name

        if(listAutoBot.size > startPos+1) {
            for (i in startPos+1 until listAutoBot.size) {
                val name = listAutoBot[i].name
                survivorsNames = ", $name"
            }
        }

        return survivorsNames
    }
}