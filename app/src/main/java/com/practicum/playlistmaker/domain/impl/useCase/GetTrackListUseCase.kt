package com.practicum.playlistmaker.domain.impl.useCase

import com.practicum.playlistmaker.domain.entity.Track
import com.practicum.playlistmaker.domain.model.Resource
import com.practicum.playlistmaker.domain.api.GetTrackListApi

class GetTrackListUseCase(
    private val trackListRepository: GetTrackListApi
) {
    fun execute(expression: String): List<Track> {
        when (val response = trackListRepository.getTrackList(expression)) {
            is Resource.Error -> return response as List<Track>
            is Resource.Success -> return emptyList()
        }
    }
}