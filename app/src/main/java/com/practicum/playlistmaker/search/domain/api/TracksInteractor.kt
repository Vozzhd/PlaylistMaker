package com.practicum.playlistmaker.search.domain.api

import com.practicum.playlistmaker.player.domain.entity.Track

interface TracksInteractor {
    fun searchTracks(expression: String, consumer: TracksConsumer)
    interface TracksConsumer {
        fun consume(foundTracks: List<Track>?, errorMessage: String?)
    }
}
