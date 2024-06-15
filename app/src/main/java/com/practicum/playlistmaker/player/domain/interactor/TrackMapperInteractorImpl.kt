package com.practicum.playlistmaker.player.domain.interactor

import com.practicum.playlistmaker.player.domain.api.TrackMapperInteractor
import com.practicum.playlistmaker.player.domain.api.TrackMapperRepository
import com.practicum.playlistmaker.player.domain.entity.Track
import com.practicum.playlistmaker.player.domain.model.TrackPresentation

class TrackMapperInteractorImpl(private val trackMapperRepository: TrackMapperRepository) :
    TrackMapperInteractor {

    override fun map(track: Track): TrackPresentation {
        return trackMapperRepository.map(track)
    }
}