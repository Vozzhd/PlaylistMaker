package com.practicum.playlistmaker.data

import android.content.SharedPreferences
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.practicum.playlistmaker.domain.models.Track
import com.practicum.playlistmaker.presentation.ui.SearchActivity

class SearchHistory(private val sharedPreferences: SharedPreferences) {
    companion object {
         var historyList: MutableList<Track> = mutableListOf()
    }

    private val gson = GsonBuilder().setPrettyPrinting().create()
    fun initHistoryList() {
        val valueFromSharedPreferences = sharedPreferences.getString(SearchActivity.SAVE_HISTORY_KEY, "")
        historyList = if (valueFromSharedPreferences.equals("")) mutableListOf()
        else gson.fromJson(valueFromSharedPreferences, object : TypeToken<List<Track>>() {}.type)
    }

    private fun putSavedTracksToSharedPreferences(tracks: MutableList<Track>) {
        sharedPreferences.edit()
            .putString(SearchActivity.SAVE_HISTORY_KEY, gson.toJson(tracks))
            .apply()
    }

    fun addToHistoryList(track: Track) {
        historyList.remove(track)
        historyList.add(0,track)
        while (historyList.size > 10) historyList.removeLast()
        putSavedTracksToSharedPreferences(historyList)
    }

    fun clearHistoryList() {
        historyList.clear()
        putSavedTracksToSharedPreferences(historyList)
    }
}

