package com.practicum.playlistmaker.domain.api

import com.practicum.playlistmaker.domain.entity.Track
import com.practicum.playlistmaker.domain.model.Resource

interface GetTrackListUseCase {
    fun getTrackList(expression: String): Resource<List<Track>>
}