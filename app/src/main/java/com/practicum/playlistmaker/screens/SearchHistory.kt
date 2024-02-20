package com.practicum.playlistmaker.screens

import android.content.SharedPreferences
import com.google.gson.Gson
import com.practicum.playlistmaker.Track
import org.json.JSONObject

class SearchHistory(sharedPreferences: SharedPreferences) {

    val gson = Gson()
    val sharedPreferences = sharedPreferences
    fun getSavedTracks() {
        val json = sharedPreferences.getString("Saved history key", "")
        val obj = JSONObject(sharedPreferences.getString("Saved history key", ""))
    }

    fun getTrackForSave(track: Track) {

    }

    fun clearHistory() {

    }

    fun add(track: Track) {

    }
}