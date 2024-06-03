package com.practicum.playlistmaker.search.data.dto

import com.practicum.playlistmaker.search.data.network.NetworkResponse

data class PlaylistReceiveResponse(val trackDtoList: List<TrackDto>) : NetworkResponse() {
}