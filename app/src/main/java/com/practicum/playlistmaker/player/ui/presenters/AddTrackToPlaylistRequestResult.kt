package com.practicum.playlistmaker.player.ui.presenters

sealed interface AddTrackToPlaylistRequestResult {
    data class Success (val playlistName : String) : AddTrackToPlaylistRequestResult
    data object Error : AddTrackToPlaylistRequestResult
}