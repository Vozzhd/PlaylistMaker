package com.practicum.playlistmaker.search.data.network

interface PlaylistReceivingNetworkClient {
    fun getTrackList(expression: String): NetworkResponse

}