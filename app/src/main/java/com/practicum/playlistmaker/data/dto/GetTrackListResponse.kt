package com.practicum.playlistmaker.data.dto
import com.practicum.playlistmaker.data.model.TrackDto

data class GetTrackListResponse(val trackDtoList: List<TrackDto>) : NetworkResponse() {
}