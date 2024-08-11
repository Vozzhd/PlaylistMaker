package com.practicum.playlistmaker.search.domain.useCase

import com.practicum.playlistmaker.player.domain.entity.Track
import java.util.concurrent.Executors
import com.practicum.playlistmaker.utilities.Resource
import com.practicum.playlistmaker.search.domain.api.TracksInteractor
import com.practicum.playlistmaker.search.domain.api.TracksRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TracksInteractorImplementation(private val trackListRepository: TracksRepository) : TracksInteractor {

    override fun searchTracks(expression: String): Flow<Pair<List<Track>?, String?>> {

        return trackListRepository.searchTracks(expression).map { result ->
            when (result) {
                is Resource.Success -> Pair(result.data, null)
                is Resource.Error -> Pair(null, result.message)
            }
        }
    }
}