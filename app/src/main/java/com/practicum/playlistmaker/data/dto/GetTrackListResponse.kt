package com.practicum.playlistmaker.data.dto

data class GetTrackListResponse(val trackDtoList: List<TrackDto>) : NetworkResponse() {
}