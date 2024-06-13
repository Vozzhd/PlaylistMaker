package com.practicum.playlistmaker.search.data.local

import android.content.SharedPreferences
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.practicum.playlistmaker.player.domain.entity.Track
import com.practicum.playlistmaker.search.ui.SearchActivity

class HistoryRepositoryImplementation(private val sharedPreferences: SharedPreferences) : HistoryRepository {
    companion object {
         var historyList: MutableList<Track> = mutableListOf()
    }

    private val gson = GsonBuilder().setPrettyPrinting().create()
    override fun initHistoryList() {
        val valueFromSharedPreferences = sharedPreferences.getString(SearchActivity.SAVED_HISTORY_KEY, "")
        historyList = if (valueFromSharedPreferences.equals("")) mutableListOf()
        else gson.fromJson(valueFromSharedPreferences, object : TypeToken<List<Track>>() {}.type)
    }

    override fun putSavedTracksToSharedPreferences(tracks: MutableList<Track>) {
        sharedPreferences.edit()
            .putString(SearchActivity.SAVED_HISTORY_KEY, gson.toJson(tracks))
            .apply()
    }

    override fun addToHistoryList(track: Track) {
        historyList.remove(track)
        historyList.add(0,track)
        while (historyList.size > 10) historyList.removeLast()
        putSavedTracksToSharedPreferences(historyList)
    }

    override fun getHistoryList(): MutableList<Track> {
        return historyList
    }

    override fun clearHistoryList() {
        historyList.clear()
        putSavedTracksToSharedPreferences(historyList)
    }
}

