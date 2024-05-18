package com.practicum.playlistmaker.search.data.dto

import com.practicum.playlistmaker.search.data.network.NetworkResponse

data class TrackGettingListResponse(val trackDtoList: List<TrackDto>) : NetworkResponse() {
}