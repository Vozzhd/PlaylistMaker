package com.practicum.playlistmaker.player.domain.interactor

import com.practicum.playlistmaker.player.domain.api.GetTrackRepository
import com.practicum.playlistmaker.player.domain.api.GetTrackUseCase
import com.practicum.playlistmaker.player.domain.entity.Track

class GetTrackUseCaseImpl(private val getTrackRepository: GetTrackRepository ): GetTrackUseCase {
    override fun execute(json: Track): Track {
        return getTrackRepository.execute(json)
    }
}