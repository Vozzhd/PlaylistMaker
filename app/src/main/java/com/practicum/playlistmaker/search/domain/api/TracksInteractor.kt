package com.practicum.playlistmaker.search.domain.api

import com.practicum.playlistmaker.player.domain.entity.Track
import kotlinx.coroutines.flow.Flow


interface TracksInteractor {
    fun searchTracks(expression: String) : Flow<Pair<List<Track>?, String?>>
}
