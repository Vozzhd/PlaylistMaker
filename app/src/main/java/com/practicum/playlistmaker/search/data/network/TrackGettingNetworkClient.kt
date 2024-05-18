package com.practicum.playlistmaker.search.data.network
import com.practicum.playlistmaker.search.data.network.NetworkResponse

interface TrackGettingNetworkClient {
    fun getTrackList(expression: String): NetworkResponse
}