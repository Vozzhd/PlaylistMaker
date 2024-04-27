package com.practicum.playlistmaker.data.mapper

import com.practicum.playlistmaker.data.dto.TrackDto
import com.practicum.playlistmaker.domain.entity.Track

object GetTrackMapper {
    fun map(tracksDto: List<TrackDto>): List<Track> {
        return tracksDto as List<Track>
    }
}