package com.practicum.playlistmaker.search.domain.useCase

import android.content.SharedPreferences
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.practicum.playlistmaker.player.domain.entity.Track
import com.practicum.playlistmaker.search.ui.TrackSearchActivity

class SearchHistory(private val sharedPreferences: SharedPreferences) {
    companion object {
         var historyList: MutableList<Track> = mutableListOf()
    }




    private val gson = GsonBuilder().setPrettyPrinting().create()
    fun initHistoryList() {
        val valueFromSharedPreferences = sharedPreferences.getString(TrackSearchActivity.SAVE_HISTORY_KEY, "")
        historyList = if (valueFromSharedPreferences.equals("")) mutableListOf()
        else gson.fromJson(valueFromSharedPreferences, object : TypeToken<List<Track>>() {}.type)
    }

    private fun putSavedTracksToSharedPreferences(tracks: MutableList<Track>) {
        sharedPreferences.edit()
            .putString(TrackSearchActivity.SAVE_HISTORY_KEY, gson.toJson(tracks))
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

