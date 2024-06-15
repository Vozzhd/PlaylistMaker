package com.practicum.playlistmaker.player.domain.api

import com.practicum.playlistmaker.player.domain.entity.Track
import com.practicum.playlistmaker.player.domain.model.TrackPresentation

interface TrackMapperInteractor {
    fun map(track: Track) : TrackPresentation
}