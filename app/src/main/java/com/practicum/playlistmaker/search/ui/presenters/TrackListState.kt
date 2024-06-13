package com.practicum.playlistmaker.search.ui.presenters
import com.practicum.playlistmaker.player.domain.entity.Track

sealed interface TrackListState {

    object Loading : TrackListState

    data class ContentFromNetwork(
        val tracks: List<Track>
    ) : TrackListState

    data class ContentFromHistory(
        val tracks: List<Track>
    ) : TrackListState

    data class Error(
        val errorMessage: String
    ) : TrackListState

    data class Empty(
        val message: String
    ) : TrackListState
}