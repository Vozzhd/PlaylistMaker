package com.practicum.playlistmaker.mediaLibrary.domain.impl

import com.practicum.playlistmaker.mediaLibrary.data.converters.TrackDbConverter
import com.practicum.playlistmaker.mediaLibrary.data.db.AppDatabase
import com.practicum.playlistmaker.mediaLibrary.data.db.entity.TrackEntity
import com.practicum.playlistmaker.mediaLibrary.domain.api.FavoriteTrackRepository
import com.practicum.playlistmaker.player.domain.entity.Track
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FavoriteTrackRepositoryImpl(
    private val appDatabase: AppDatabase,
    private val trackDbConvertor: TrackDbConverter
) : FavoriteTrackRepository {

    override suspend fun addToFavorite(track: Track) {
        appDatabase.trackDao().insertTrackToFavoriteTable(trackDbConvertor.map(track))
    }

    override suspend fun deleteFromFavorite(track: Track) {
        appDatabase.trackDao().deleteTrackFromFavoriteTable(trackDbConvertor.map(track))
    }

    override fun getFavoriteTrackList(): Flow<List<Track>> = flow {
        val favoriteTrackList = appDatabase.trackDao().getFavoriteTracksTable()
        emit(convertFromTrackEntity(favoriteTrackList))
    }

    fun convertFromTrackEntity(favoriteTrackList: List<TrackEntity>): List<Track> {
        return favoriteTrackList.map { trackList ->
            trackDbConvertor.map(trackList)
        }
    }
}