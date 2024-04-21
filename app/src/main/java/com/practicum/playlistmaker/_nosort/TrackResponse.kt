package com.practicum.playlistmaker._nosort

import com.practicum.playlistmaker.domain.entity.Track

class TrackResponse (
    val resultCount: Int,
    val results: List<Track>
)