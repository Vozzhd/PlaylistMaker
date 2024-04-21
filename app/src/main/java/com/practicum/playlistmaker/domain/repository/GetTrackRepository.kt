package com.practicum.playlistmaker.domain.repository

import com.practicum.playlistmaker.domain.entity.Track
import com.practicum.playlistmaker.domain.model.Resource

interface GetTrackRepository {
    fun getTrackList(expression: String): Resource<List<Track>>
}