package com.practicum.playlistmaker.search.domain.api

import com.practicum.playlistmaker.player.domain.entity.Track

interface HistoryInteractor {
    fun initHistoryList()
    fun putSavedTracksToSharedPreferences(tracks: MutableList<Track>)
    fun addToHistoryList(track: Track)
}