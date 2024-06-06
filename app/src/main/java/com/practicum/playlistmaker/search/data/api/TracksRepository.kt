package com.practicum.playlistmaker.search.data.api
import com.practicum.playlistmaker.player.domain.entity.Track
import com.practicum.playlistmaker.player.domain.model.Resource

interface TracksRepository {
    fun searchTracks(expression: String) : Resource<List<Track>>

}