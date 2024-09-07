package com.practicum.playlistmaker.player.ui.presenters

sealed interface AddTrackToPlaylistRequestResult {
    data object Success : AddTrackToPlaylistRequestResult
    data object Error : AddTrackToPlaylistRequestResult
}