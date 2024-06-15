package com.practicum.playlistmaker.search.domain.api
import com.practicum.playlistmaker.player.domain.entity.Track
import com.practicum.playlistmaker.utilities.Resource

interface TracksRepository {
    fun searchTracks(expression: String) : Resource<List<Track>>

}