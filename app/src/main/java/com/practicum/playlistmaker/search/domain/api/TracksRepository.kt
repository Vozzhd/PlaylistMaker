package com.practicum.playlistmaker.search.domain.api
import com.practicum.playlistmaker.player.domain.entity.Track
import com.practicum.playlistmaker.utilities.Resource
import kotlinx.coroutines.flow.Flow

interface TracksRepository {
    fun searchTracks(expression: String) : Flow<Resource<List<Track>>>
}