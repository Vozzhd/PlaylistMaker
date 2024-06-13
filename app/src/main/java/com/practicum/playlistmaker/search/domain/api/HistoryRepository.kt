package com.practicum.playlistmaker.search.domain.api

import com.practicum.playlistmaker.player.domain.entity.Track

interface HistoryRepository {
    fun putSavedTracksToSharedPreferences(tracks: MutableList<Track>)
    fun addToHistoryList(track: Track)
    fun getHistoryList(): MutableList<Track>
    fun clearHistoryList()
}