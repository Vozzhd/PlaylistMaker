package com.practicum.playlistmaker.mediaLibrary.favorite.domain.api

import com.practicum.playlistmaker.player.domain.entity.Track
import kotlinx.coroutines.flow.Flow

interface FavoriteTrackRepository {
    suspend fun addToFavorite(track: Track)
    suspend fun deleteFromFavorite(track: Track)
    fun getFavoriteTrackList(): Flow<List<Track>>
}