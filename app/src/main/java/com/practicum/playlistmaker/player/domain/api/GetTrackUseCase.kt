package com.practicum.playlistmaker.player.domain.api

import com.practicum.playlistmaker.player.domain.entity.Track

interface GetTrackUseCase {
    fun execute(json:Track) : Track
}