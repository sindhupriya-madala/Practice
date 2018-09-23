package com.vemuru.transformers.service

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.manoj.transformersae.App
import com.manoj.transformersae.model.BotModel
import com.manoj.transformersae.model.Model
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

    fun requestCreateTransformer(botModel: BotModel, id:String, appContext: Context) : Boolean {
        try {
            val requestContent:String = getGson().toJson(botModel)
            val requestBody:RequestBody = RequestBody.create(MediaType.parse("application/json"), requestContent)
            val httpResponse = getClient().newCall(buildRequest(id, requestBody)).execute()
            return if(httpResponse.isSuccessful) {
                val responseString = httpResponse.body().string()
                val listType = object : TypeToken<BotModel>() {}.type
                val responseBotModel:BotModel = getGson().fromJson(responseString, listType)
                Model.getInstance(appContext).insertTransformerDB(responseBotModel)
                true
            } else {
                false
            }

        } catch (e : Exception) {
            return false
        }
    }

    fun requestListTransformers(appContext:Context, id:String) {
        try {
            val request = Request.Builder().header("Authorization", "Bearer " + id)
                    .header("Content-Type", "application/json")
                    .url(BASE_URL).get().build()
            val httpResponse = getClient().newCall(request).execute()
            val response = httpResponse.body().string()
            if (httpResponse.isSuccessful) {
                val jsonArray = JSONObject(response).getJSONArray("transformers")
                val listType = object : TypeToken<List<BotModel>>() {}.type
                val list: List<BotModel> = getGson().fromJson(jsonArray.toString(), listType)
                Model.getInstance(appContext).insertTransformersDB(list)
            } else {
                //TODO: show error
            }
        } catch (e:Exception) {
            //TODO: show error
        }
    }

    fun requestUpdateTransformer(botModel: BotModel, id:String, context: Context) : Boolean {
        try {
            val requestContent:String = getGson().toJson(botModel)
            val requestBody:RequestBody = RequestBody.create(MediaType.parse("application/json"), requestContent)
            val request = Request.Builder().header("Authorization", "Bearer " + id)
                    .header("Content-Type", "application/json")
                    .url(BASE_URL).put(requestBody).build()
            val httpResponse = getClient().newCall(request).execute()
            return if(httpResponse.isSuccessful) {
                val responseString = httpResponse.body().string()
                val listType = object : TypeToken<BotModel>() {}.type
                val responseBotModel:BotModel = getGson().fromJson(responseString, listType)
                Model.getInstance(context).updateTransformerDB(responseBotModel)
                true
            } else {
                false
            }

        } catch (e : Exception) {
            return false
        }
        return false
    }

    fun requestDeleteTransformer(botModel: BotModel, id:String, context: Context) :Boolean {
        try {

            val request = Request.Builder().header("Authorization", "Bearer " + id)
                    .header("Content-Type", "application/json")
                    .url(BASE_URL + "/"+botModel.id).delete().build()
            val httpResponse = getClient().newCall(request).execute()
            return if(httpResponse.isSuccessful) {
                Model.getInstance(context).deleteTransformerDB(botModel)
                true
            } else {
                false
            }

        } catch (e : Exception) {
            return false
        }
        return false
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