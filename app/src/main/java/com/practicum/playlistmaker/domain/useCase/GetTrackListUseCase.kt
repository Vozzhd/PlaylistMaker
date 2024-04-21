package com.practicum.playlistmaker.domain.useCase

import com.practicum.playlistmaker.data.repository.GetTrackRepositoryImpl
import com.practicum.playlistmaker.domain.entity.Track
import com.practicum.playlistmaker.domain.model.Resource
import com.practicum.playlistmaker.domain.repository.GetTrackRepository
import okhttp3.Response

class GetTrackListUseCase(
    private val trackListRepository: GetTrackRepository
) {
    fun execute(expression: String): List<Track> {
        when (val response = trackListRepository.getTrackList(expression)) {
            is Resource.Error -> return response as List<Track>
            is Resource.Success -> return emptyList()
        }
    }
}