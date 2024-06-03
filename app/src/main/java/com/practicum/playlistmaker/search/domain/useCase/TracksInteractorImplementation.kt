package com.practicum.playlistmaker.search.domain.useCase

import com.practicum.playlistmaker.player.domain.model.Resource
import java.util.concurrent.Executors
import com.practicum.playlistmaker.search.domain.api.TracksInteractor
import com.practicum.playlistmaker.search.domain.api.TracksRepository
class TracksInteractorImplementation(private val trackListRepository: TracksRepository) :
    TracksInteractor {

    private val executor = Executors.newCachedThreadPool()


//    fun execute(expression: String): List<Track> {
//        when (val response = trackListRepository.searchTracks(expression)) {
//            is Resource.Error -> return response as List<Track>
//            is Resource.Success -> return emptyList()
//            else -> {
//                return emptyList()
//            }
//        }
//    }

    override fun searchTracks(expression: String, consumer: TracksInteractor.TracksConsumer) {
        executor.execute {
            when (val resource = trackListRepository.searchTracks(expression)) {
                is Resource.Error -> consumer.consume(resource.data, null)
                is Resource.Success -> consumer.consume(null, resource.message)
            }
        }
    }
}