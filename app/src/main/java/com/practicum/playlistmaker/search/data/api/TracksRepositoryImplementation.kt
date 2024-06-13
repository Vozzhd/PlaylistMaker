package com.practicum.playlistmaker.search.data.api

import com.practicum.playlistmaker.player.domain.entity.Track
import com.practicum.playlistmaker.player.domain.model.Resource
import com.practicum.playlistmaker.search.data.dto.TrackSearchRequest
import com.practicum.playlistmaker.search.data.dto.TrackSearchResponse
import com.practicum.playlistmaker.search.data.network.NetworkClient
import com.practicum.playlistmaker.search.domain.api.TracksRepository

class TracksRepositoryImplementation(private val networkClient: NetworkClient) : TracksRepository {
    override fun searchTracks(expression: String): Resource<List<Track>> {

        val response = networkClient.doRequest(TrackSearchRequest(expression))
        return when (response.resultCode) {
            -1 -> {
                Resource.Error("Проверьте подключение к интернету")
            }
            200 -> {
                Resource.Success((response as TrackSearchResponse).results.map {
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
                        it.previewUrl
                    )
                })
            }
            else -> {
                Resource.Error("Произошла ошибка сети")
            }
        }
    }
}


//    override fun getTrackList(expression: String): Resource<List<Track>> {
//        val response = trackGettingNetworkClient.getTrackList(expression)
//
//        return if (response is TrackGettingListResponse) {
//            val result = TrackGettingMapper.map(response.trackDtoList)
//            Resource.Success(result)
//        } else {
//            Resource.Error("Произошла ошибка сети")
//        }
//    }