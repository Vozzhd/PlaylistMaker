package com.practicum.playlistmaker.search.data.local

import android.content.SharedPreferences
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.practicum.playlistmaker.player.domain.entity.Track
import com.practicum.playlistmaker.search.domain.api.HistoryRepository
import com.practicum.playlistmaker.utilities.SAVED_HISTORY_KEY

class HistoryRepositoryImplementation(private val sharedPreferences: SharedPreferences) :
    HistoryRepository {

    private var historyList: MutableList<Track> = mutableListOf()
    private val gson = GsonBuilder().setPrettyPrinting().create()

    init {
        val valueFromSharedPreferences = sharedPreferences.getString(SAVED_HISTORY_KEY, "")
        historyList = if (valueFromSharedPreferences.equals("")) mutableListOf()
        else gson.fromJson(valueFromSharedPreferences, object : TypeToken<List<Track>>() {}.type)
    }
    override fun putSavedTracksToSharedPreferences(tracks: MutableList<Track>) {
        sharedPreferences.edit()
            .putString(SAVED_HISTORY_KEY, gson.toJson(tracks))
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

