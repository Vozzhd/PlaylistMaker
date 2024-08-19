package com.practicum.playlistmaker.search.data.api

import com.practicum.playlistmaker.mediaLibrary.data.db.AppDatabase
import com.practicum.playlistmaker.player.domain.entity.Track
import com.practicum.playlistmaker.utilities.Resource
import com.practicum.playlistmaker.search.data.dto.TrackSearchRequest
import com.practicum.playlistmaker.search.data.dto.TrackSearchResponse
import com.practicum.playlistmaker.search.data.network.NetworkClient
import com.practicum.playlistmaker.search.domain.api.TracksRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class TracksRepositoryImplementation(
    private val networkClient: NetworkClient,
    private val appDatabase: AppDatabase
) : TracksRepository {
    override fun searchTracks(expression: String): Flow<Resource<List<Track>>> = flow {

        val response = networkClient.doRequest(TrackSearchRequest(expression))
        when (response.resultCode) {
            -1 -> {
                emit(Resource.Error("Internet error"))
            }

            200 -> {
                with(response as TrackSearchResponse) {
                    val data = results.map {
                        Track(
                            it.trackName,
                            it.artistName,
                            it.trackTimeMillis,
                            it.artworkUrl100,
                            it.trackId,
                            it.collectionName,
                            it.releaseDate,
                            it.primaryGenreName,
                            it.country,
                            it.previewUrl,
                            isFavorite = false
                        )
                    }
                    val trackFavoriteIDs = appDatabase.trackDao().getFavoriteIDsFromFavoriteTable()
                    for (track in data) {
                        if (trackFavoriteIDs.contains(track.trackId))
                            track.isFavorite = true
                    }
                    emit(Resource.Success(data))
                }
            }

            else -> {
                emit(Resource.Error("Internet error"))
            }
        }
    }
}