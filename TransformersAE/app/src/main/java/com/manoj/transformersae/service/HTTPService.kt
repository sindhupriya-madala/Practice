package com.vemuru.transformers.service

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.manoj.transformersae.App
import com.manoj.transformersae.model.BotModel
import com.manoj.transformersae.model.Model.Companion.mCreateRequestSuccess
import com.manoj.transformersae.service.AppDBService
import com.manoj.transformersae.util.AppUtill
import kotlinx.coroutines.experimental.launch
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONObject
import java.io.File

/**
 * Created by Manoj Vemuru on 2018-09-19.
 */
class HTTPService private constructor() {
    private val mAllSparkURL = "https://transformers-api.firebaseapp.com/allspark"
    private val BASE_URL = "https://transformers-api.firebaseapp.com/transformers";
    private val CACHE_PATH = App.getContext().cacheDir.absolutePath
    private val CACHE_SIZE_IN_MB = (10 * 1024 * 1024).toLong()

    companion object {
        private var INSTANCE:HTTPService? = null

        fun getInstance() : HTTPService {
            if(INSTANCE == null) {
                INSTANCE = HTTPService()
            }
            return INSTANCE as HTTPService
        }
    }

    fun requestToken(context: Context) {
        launch {
            val response = getClient().newCall(Request.Builder().url(mAllSparkURL).build()).execute()
            val token:String = response.body().string()
            if(token != null) {
                AppUtill.saveToken(context, token)
            }
            (context.applicationContext as App).token = token
        }
    }

    fun requestCreateTransformer(botModel: BotModel, id:String, appContext: Context) {
        launch {
            try {
                val requestContent:String = getGson().toJson(botModel)
                val requestBody:RequestBody = RequestBody.create(MediaType.parse("application/json"), requestContent)
                val httpResponse = getClient().newCall(buildRequest(id, requestBody)).execute()
                val responseString = httpResponse.body().string()
                val listType = object : TypeToken<BotModel>() {}.type
                val responseBotModel:BotModel = getGson().fromJson(responseString, listType)
                AppDBService.getAppDatabase(appContext).botDao().insertBot(responseBotModel)
                mCreateRequestSuccess.postValue(true)
            } catch (e : Exception) {
                mCreateRequestSuccess.postValue(false)
            }
        }
    }

    fun requestListTransformers(appContext:Context, id:String) : List<BotModel> {
        launch {
            val request = Request.Builder().header("Authorization", "Bearer "+id)
                    .header("Content-Type", "application/json")
                    .url(BASE_URL).get().build()
            val httpResponse = getClient().newCall(request).execute()
            val response = httpResponse.body().string()
            val jsonArray = JSONObject(response).getJSONArray("transformers")
            val listType = object : TypeToken<List<BotModel>>() {}.type
            val list : List<BotModel> = getGson().fromJson(jsonArray.toString(), listType);
            AppDBService.getAppDatabase(appContext.applicationContext).botDao().insertAllBot(list)

        }
        return AppDBService.getAppDatabase(appContext).botDao().all
    }

    fun requestUpdateTransformer(botModel: BotModel, id:String) {

    }

    fun requestDeleteTransformer(botModel: BotModel, id:String) {

    }

    private fun buildRequest(id : String, requestBody:RequestBody) : Request {
        return Request.Builder().header("Authorization", "Bearer "+id)
                .header("Content-Type", "application/json")
                .url(BASE_URL).post(requestBody)
                .build();
    }

    private fun getClient(): OkHttpClient {
        return OkHttpClient.Builder()
                .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .cache(Cache(File(CACHE_PATH), CACHE_SIZE_IN_MB))
                .build()
    }

    private fun getGson(): Gson {
        return GsonBuilder().create()
    }
}