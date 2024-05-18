package com.practicum.playlistmaker.practice

sealed class TrackScreenState {
    object Loading: TrackScreenState()
    data class Content(
        var trackModel: TrackModel,
    ): TrackScreenState()
}