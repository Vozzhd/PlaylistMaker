package com.practicum.playlistmaker.player.data

import com.practicum.playlistmaker.player.domain.api.GetTrackRepository
import com.practicum.playlistmaker.player.domain.entity.Track

class GetTrackRepositoryImpl : GetTrackRepository {
    override fun execute(json: Track): Track {
        return  json
    }
}