package com.practicum.playlistmaker.mediaLibrary.favorite.ui.model

import com.practicum.playlistmaker.player.domain.entity.Track

sealed interface FavoriteListState {
    data class Content(
        val favoriteList: List<Track>
    ) : FavoriteListState

    data object Empty : FavoriteListState
}