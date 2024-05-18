package com.practicum.playlistmaker.search.data.mapper

import com.practicum.playlistmaker.search.data.dto.TrackDto
import com.practicum.playlistmaker.player.domain.entity.Track

object TrackGettingMapper {
    fun map(tracksDto: List<TrackDto>): List<Track> {
        return tracksDto as List<Track>
    }
}