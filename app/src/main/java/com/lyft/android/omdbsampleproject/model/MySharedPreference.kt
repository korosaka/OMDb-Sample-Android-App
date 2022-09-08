package com.lyft.android.omdbsampleproject.model

import android.content.Context
import android.content.Context.MODE_PRIVATE
import org.json.JSONArray

class MySharedPreference {

    companion object {
        const val SHARED_FILE_NAME = "shared_preference_file"
        const val FAVORITE_KEY = "my_favorite"
    }

    private fun saveString(key: String, value: String, context: Context) {
        val sp = context.getSharedPreferences(SHARED_FILE_NAME, MODE_PRIVATE)
        val editor = sp.edit()
        editor.putString(key, value)
        editor.apply()
    }

    private fun getStringFromSP(key: String, context: Context): String {
        val sp = context.getSharedPreferences(SHARED_FILE_NAME, MODE_PRIVATE)
        return sp.getString(key, "") ?: ""
    }

    fun saveFavorites(movieId: String, context: Context) {
        val array = getFavorites(context)
        if (array.contains(movieId)) return
        array.add(movieId)

        saveString(FAVORITE_KEY, JSONArray(array).toString(), context)
    }

    fun getFavorites(context: Context) : ArrayList<String> {
        val sp = context.getSharedPreferences(SHARED_FILE_NAME, MODE_PRIVATE)
        val jsonArray = JSONArray(sp.getString(FAVORITE_KEY, "[]"))
        val array = ArrayList<String>()

        for (i in 0 until jsonArray.length()) array.add(jsonArray.get(i).toString())
        return array
    }

//    fun saveMovie(key: String, value: Any, context: Context) {
//        val gson = Gson()
//        val json = gson.toJson(value)
//        saveString(key, json, context)
//    }
//
//    fun getMovie(key: String, context: Context): MovieData {
//        val gson = Gson()
//        val json = getStringFromSP(key, context)
//        return gson.fromJson(json, MovieData::class.java)
//    }
}