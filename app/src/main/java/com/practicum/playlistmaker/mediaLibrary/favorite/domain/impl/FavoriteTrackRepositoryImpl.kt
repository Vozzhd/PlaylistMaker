package com.practicum.playlistmaker.mediaLibrary.favorite.domain.impl

import com.practicum.playlistmaker.mediaLibrary.favorite.data.converters.TrackDbConverter
import com.practicum.playlistmaker.utilities.AppDatabase
import com.practicum.playlistmaker.mediaLibrary.favorite.data.db.entity.TrackEntity
import com.practicum.playlistmaker.mediaLibrary.favorite.domain.api.FavoriteTrackRepository
import com.practicum.playlistmaker.player.domain.entity.Track
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FavoriteTrackRepositoryImpl(
    private val appDatabase: AppDatabase,
    private val trackDbConvertor: TrackDbConverter
) : FavoriteTrackRepository {

    override suspend fun addToFavorite(track: Track) {
        appDatabase.daoInterface().insertTrackToFavoriteTable(trackDbConvertor.map(track))
    }

    override suspend fun deleteFromFavorite(track: Track) {
        appDatabase.daoInterface().deleteTrackFromFavoriteTable(trackDbConvertor.map(track))
    }

    override fun getFavoriteTrackList(): Flow<List<Track>> = flow {
        val favoriteTrackList = appDatabase.daoInterface().getFavoriteTracksTable()
        emit(convertFromTrackEntity(favoriteTrackList))
    }

    fun convertFromTrackEntity(favoriteTrackList: List<TrackEntity>): List<Track> {
        return favoriteTrackList.map { trackList ->
            trackDbConvertor.map(trackList)
        }
    }
}