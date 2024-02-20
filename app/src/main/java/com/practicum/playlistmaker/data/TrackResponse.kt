package com.practicum.playlistmaker.data

import com.practicum.playlistmaker.Track

class TrackResponse (
    val resultCount: Int,
    val results: List<Track>
)