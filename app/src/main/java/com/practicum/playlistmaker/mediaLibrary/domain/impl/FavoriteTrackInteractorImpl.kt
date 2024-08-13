package com.practicum.playlistmaker.mediaLibrary.domain.impl

import com.practicum.playlistmaker.mediaLibrary.domain.api.FavoriteTrackInteractor
import com.practicum.playlistmaker.mediaLibrary.domain.api.FavoriteTrackRepository
import com.practicum.playlistmaker.player.domain.entity.Track
import kotlinx.coroutines.flow.Flow

class FavoriteTrackInteractorImpl(private val favoriteTrackRepository: FavoriteTrackRepository) : FavoriteTrackInteractor {

    override suspend fun addToFavorite(track: Track) {
        favoriteTrackRepository.addToFavorite(track)
    }

    override suspend fun deleteFromFavorite(track: Track) {
        favoriteTrackRepository.deleteFromFavorite(track)
    }

    override suspend fun getFavoriteTrackList(): Flow<List<Track>> {
        return favoriteTrackRepository.getFavoriteTrackList()
    }
}