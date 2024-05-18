package com.practicum.playlistmaker.search.domain.api

import com.practicum.playlistmaker.player.domain.entity.Track
import com.practicum.playlistmaker.player.domain.model.Resource

interface TrackGettingListUseCase {
    fun getTrackList(expression: String): Resource<List<Track>>
}