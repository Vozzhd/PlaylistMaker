package com.practicum.playlistmaker.screens

import android.content.SharedPreferences
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.practicum.playlistmaker.Track

class SearchHistory(private val sharedPreferences: SharedPreferences) {
    companion object {
         var historyList: MutableList<Track> = mutableListOf()
    }

    val gson = GsonBuilder().setPrettyPrinting().create()
    fun initHistoryList() {
        historyList = if (getSavedTracksFromSharedPreferences().isEmpty()) {
            mutableListOf()
        } else {
            getSavedTracksFromSharedPreferences()
        }
    }
     fun getSavedTracksFromSharedPreferences(): MutableList<Track> {
        val valueFromSharedPreferences =
            sharedPreferences.getString(SearchActivity.SAVE_HISTORY_KEY, "")
        return gson.fromJson(valueFromSharedPreferences, object : TypeToken<List<Track>>() {}.type)
    }

    private fun putSavedTracksToSharedPreferences(tracks: MutableList<Track>) {
        val trackHistoryListInJSON = gson.toJson(tracks)
        sharedPreferences.edit()
            .putString(SearchActivity.SAVE_HISTORY_KEY, trackHistoryListInJSON)
            .apply()
    }

    fun addToHistoryList(track: Track) {
        historyList.remove(track)
        historyList.add(track)
        if (historyList.size >= 10) historyList.removeFirst()
        putSavedTracksToSharedPreferences(historyList)
    }

    fun clearHistoryList() {
        historyList.clear()
        putSavedTracksToSharedPreferences(historyList)
    }
}

