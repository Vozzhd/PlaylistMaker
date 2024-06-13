package com.practicum.playlistmaker.search.domain.useCase
import java.util.concurrent.Executors
import com.practicum.playlistmaker.player.domain.model.Resource
import com.practicum.playlistmaker.search.domain.api.TracksInteractor
import com.practicum.playlistmaker.search.domain.api.TracksRepository
class TracksInteractorImplementation(private val trackListRepository: TracksRepository) :
    TracksInteractor {

    private val executor = Executors.newCachedThreadPool()

    override fun searchTracks(expression: String, consumer: TracksInteractor.TracksConsumer) {
        executor.execute {
            when (val resource = trackListRepository.searchTracks(expression)) {
                is Resource.Success -> consumer.consume(resource.data, null)
                is Resource.Error -> consumer.consume(null, resource.message)
            }
        }
    }
}