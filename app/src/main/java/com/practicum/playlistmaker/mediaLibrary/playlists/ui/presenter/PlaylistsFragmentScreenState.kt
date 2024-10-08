package com.practicum.playlistmaker.mediaLibrary.playlists.ui.presenter

import com.practicum.playlistmaker.playlistManage.createPlaylist.domain.entity.Playlist

sealed interface PlaylistsFragmentScreenState {
    data class Content(
        val playlists: List<Playlist>
    ) : PlaylistsFragmentScreenState

    data object Empty : PlaylistsFragmentScreenState
}
