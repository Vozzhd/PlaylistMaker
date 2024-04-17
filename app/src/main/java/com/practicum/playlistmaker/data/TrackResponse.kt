package com.practicum.playlistmaker.data

import com.practicum.playlistmaker.domain.models.Track

class TrackResponse (
    val resultCount: Int,
    val results: List<Track>
)