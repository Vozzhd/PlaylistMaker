package com.practicum.playlistmaker.search.domain.api

import com.practicum.playlistmaker.player.domain.entity.Track

interface HistoryInteractor {
    fun putSavedTracksToSharedPreferences(tracks: MutableList<Track>)
    fun addToHistoryList(track: Track)
    fun getHistoryList(): List<Track>
    fun clearHistoryList()
}