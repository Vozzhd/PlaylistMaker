package com.practicum.playlistmaker.search.data.local

import com.practicum.playlistmaker.player.domain.entity.Track

interface HistoryRepository {
    fun initHistoryList()
    fun putSavedTracksToSharedPreferences(tracks: MutableList<Track>)
    fun addToHistoryList(track: Track)
}