package com.practicum.playlistmaker.data.network

import com.practicum.playlistmaker.domain.entity.Track

class TrackResponse (
    val resultCount: Int,
    val results: List<Track>
)