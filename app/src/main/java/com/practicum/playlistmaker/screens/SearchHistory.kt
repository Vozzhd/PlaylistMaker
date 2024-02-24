package com.practicum.playlistmaker.screens

import android.content.SharedPreferences
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.practicum.playlistmaker.Track

class SearchHistory(private val sharedPreferences: SharedPreferences) {
   companion object {
       val historyList: MutableList<Track> = mutableListOf()
    }
    val gson = GsonBuilder().setPrettyPrinting().create()
    fun getSavedTracksFromSharedPreferences(): MutableList<Track> {
        val valueFromSharedPreferences =
            sharedPreferences.getString(SearchActivity.SAVE_HISTORY_KEY, "")
        return gson.fromJson(valueFromSharedPreferences, object : TypeToken<List<Track>>() {}.type)
    }

    fun putSavedTracksToSharedPreferences(tracks: MutableList<Track>) {
        val trackHistoryListInJSON = gson.toJson(tracks)
        sharedPreferences.edit()
            .putString(SearchActivity.SAVE_HISTORY_KEY, trackHistoryListInJSON)
            .apply()
    }

    fun addToHistoryList(track: Track) {
        if (historyList.contains(track)) historyList.remove(track)
        if (historyList.size > 10) historyList.removeAt(10)
        historyList.add(track)
        putSavedTracksToSharedPreferences(historyList)
    }

    fun clearHistoryList() {
        historyList.clear()
        putSavedTracksToSharedPreferences(historyList)
    }

}
