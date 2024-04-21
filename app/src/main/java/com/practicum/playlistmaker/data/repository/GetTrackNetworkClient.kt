package com.practicum.playlistmaker.data.repository
import com.practicum.playlistmaker.data.dto.NetworkResponse

interface GetTrackNetworkClient {
    fun getTrackList(expression: String): NetworkResponse
}