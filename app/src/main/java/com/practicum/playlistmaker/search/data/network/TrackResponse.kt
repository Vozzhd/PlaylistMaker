package com.practicum.playlistmaker.search.data.network

import com.practicum.playlistmaker.player.domain.entity.Track

class TrackResponse (
    val resultCount: Int,
    val results: List<Track>
)