package com.practicum.playlistmaker.mediaLibrary.playlist.ui.presenter

import com.practicum.playlistmaker.playlistCreating.domain.entity.Playlist

sealed interface PlaylistsFragmentScreenState {
    data class Content(
        val playlists: List<Playlist>
    ) : PlaylistsFragmentScreenState

    data object Empty : PlaylistsFragmentScreenState
}
