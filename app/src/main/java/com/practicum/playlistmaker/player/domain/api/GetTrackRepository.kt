package com.practicum.playlistmaker.player.domain.api

import com.practicum.playlistmaker.player.domain.entity.Track

interface GetTrackRepository {
fun execute(json:Track) : Track
}