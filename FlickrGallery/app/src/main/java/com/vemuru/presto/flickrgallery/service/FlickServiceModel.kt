package com.vemuru.presto.flickrgallery.service

import android.arch.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.vemuru.presto.flickrgallery.App
import com.vemuru.presto.flickrgallery.model.PhotoModel
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.launch
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONObject
import java.io.File

/**
 * Created by Manoj Vemuru on 2018-09-18.
 */
class FlickServiceModel private constructor() {
    val PAGE_SIZE = 100
    private val API_KEY = "949e98778755d1982f537d56236bbb42"
    private val URL_BASE = ("https://api.flickr.com/services/rest/"
            + "?format=json&nojsoncallback=1&api_key=" + API_KEY)
    private val COLUMN_PHOTO = "photo"
    private val COLUMN_PHOTOS = "photos"
    private val URL_SEARCH = "&method=flickr.photos.search&tags=mode&per_page=$PAGE_SIZE&page="
    private val CACHE_PATH = App.getContext().cacheDir.absolutePath
    private val CACHE_SIZE_IN_MB = (10 * 1024 * 1024).toLong()
    var listPhotos = MutableLiveData<List<PhotoModel>>()

    companion object {
        val INSTANCE = FlickServiceModel()
    }

    fun loadInitialPhotos(page:Int) {
        launch {
            listPhotos.postValue(search(page))
        }
    }

    private fun search(page: Int): List<PhotoModel>? {
        val request = Request.Builder()
                .url(URL_BASE + URL_SEARCH + page)
                .build()

        val response = getClient().newCall(request).execute()
        val json = response.body().string()
        val jsonArray = JSONObject(json).getJSONObject(COLUMN_PHOTOS).getJSONArray(COLUMN_PHOTO)

        val listType = object : TypeToken<List<PhotoModel>>() {
        }.type
        return getGson().fromJson(jsonArray.toString(), listType)
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