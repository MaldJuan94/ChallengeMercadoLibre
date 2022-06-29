package com.mercadolibre.app.utils

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import kotlin.collections.ArrayList

object SharedPreferenceUtils {
    private fun getSharedPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences("data", Context.MODE_PRIVATE)
    }

    fun saveLastSearchedList(context: Context, lastItemList: ArrayList<String>) {
        val saveLastItemList = Gson().toJson(lastItemList)
        getSharedPreferences(context).edit()
            .putString("lastSearched", saveLastItemList).apply()
    }

    fun getLastSearchedList(context: Context): ArrayList<String> {
        val lastItemList =
            getSharedPreferences(context).getString("lastSearched", "")
        if (lastItemList.isNullOrEmpty())
            return ArrayList()
        return Gson().fromJson(lastItemList, ArrayList<String>()::class.java)
    }
}
