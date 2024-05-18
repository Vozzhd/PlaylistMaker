package com.practicum.playlistmaker.search.domain.useCase
import com.practicum.playlistmaker.player.domain.entity.Track
import com.practicum.playlistmaker.player.domain.model.Resource
import com.practicum.playlistmaker.search.domain.api.TrackGettingListUseCase

class TrackListFromNetworkUseCase(
    private val trackListRepository: TrackGettingListUseCase
) {
    fun execute(expression: String): List<Track> {
        when (val response = trackListRepository.getTrackList(expression)) {
            is Resource.Error -> return response as List<Track>
            is Resource.Success -> return emptyList()
            else -> {return emptyList() }
        }
    }
}