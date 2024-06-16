package com.practicum.playlistmaker.player.data

import com.google.gson.Gson
import com.practicum.playlistmaker.player.domain.api.GetTrackRepository
import com.practicum.playlistmaker.player.domain.entity.Track

class GetTrackRepositoryImpl : GetTrackRepository {
//    override fun execute(json: String): Track {
//        return Gson().fromJson(json, Track::class.java)
    override fun execute(json: Track): Track {
        return  json
    }
}