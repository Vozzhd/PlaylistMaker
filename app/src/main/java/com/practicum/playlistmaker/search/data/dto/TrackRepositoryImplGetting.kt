package com.practicum.playlistmaker.search.data.dto

import com.practicum.playlistmaker.search.data.mapper.TrackGettingMapper
import com.practicum.playlistmaker.player.domain.entity.Track
import com.practicum.playlistmaker.search.domain.api.TrackGettingListUseCase
import com.practicum.playlistmaker.player.domain.model.Resource
import com.practicum.playlistmaker.search.data.network.TrackGettingNetworkClient

class TrackRepositoryImplGetting(
    private val trackGettingNetworkClient: TrackGettingNetworkClient
) : TrackGettingListUseCase {
    override fun getTrackList(expression: String): Resource<List<Track>> {
        val response = trackGettingNetworkClient.getTrackList(expression)

        return if (response is TrackGettingListResponse) {
            val result = TrackGettingMapper.map(response.trackDtoList)
            Resource.Success(result)
        } else {
            Resource.Error("Произошла ошибка сети")
        }
    }
}