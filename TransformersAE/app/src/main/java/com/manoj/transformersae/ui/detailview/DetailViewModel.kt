package com.manoj.transformersae.ui.detailview

import android.arch.lifecycle.ViewModel
import android.content.Context
import com.manoj.transformersae.model.BotModel
import com.manoj.transformersae.model.Model
import com.manoj.transformersae.util.AppUtill
import io.reactivex.subjects.*

/**
 * Created by Manoj Vemuru on 2018-09-22.
 */
class DetailViewModel : ViewModel() {
    private lateinit var mBotModel: BotModel
    private lateinit var mContext: Context

    fun init(context: Context) {
        this.mContext = context
    }

    fun getBotModel(): BotModel {
        return mBotModel
    }

    fun setBotModel(botModel: BotModel) {
        this.mBotModel = botModel
    }

    fun save(botModel: BotModel) {
        Model.getInstance(mContext).saveTransformer(botModel, AppUtill.getSavedToken(mContext), mContext)
    }

    fun update(botModel: BotModel) {
        Model.getInstance(mContext).updateTransformer(botModel, AppUtill.getSavedToken(mContext), mContext)
    }

    fun getSuccessSubject() : PublishSubject<Boolean> {
        return Model.getInstance(mContext).mCreateRequestSuccess
    }
}
