package com.practicum.playlistmaker.player.ui.model

import com.practicum.playlistmaker.player.domain.entity.Track

sealed interface FavoriteListState {
    data class Content(
        val favoriteList: List<Track>
    ) : FavoriteListState

    data class Empty(
        val message: String
    ) : FavoriteListState
}