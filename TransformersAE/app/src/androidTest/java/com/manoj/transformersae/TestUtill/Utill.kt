package com.manoj.transformersae.TestUtill

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.manoj.transformersae.model.BotModel
import org.json.JSONObject

/**
 * Created by Manoj Vemuru on 2018-09-22.
 */
class Utill {
    companion object {
        fun parseResponseToList(response:String) : List<BotModel> {
            val jsonArray = JSONObject(response).getJSONArray("transformers")
            val listType = object : TypeToken<List<BotModel>>() {}.type
            return  getGson().fromJson(jsonArray.toString(), listType)
        }

        private fun getGson(): Gson {
            return GsonBuilder().create()
        }
    }
}