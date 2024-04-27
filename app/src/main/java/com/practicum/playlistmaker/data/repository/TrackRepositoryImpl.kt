package com.practicum.playlistmaker.data.repository

import com.practicum.playlistmaker.data.dto.GetTrackListResponse
import com.practicum.playlistmaker.data.mapper.GetTrackMapper
import com.practicum.playlistmaker.domain.entity.Track
import com.practicum.playlistmaker.domain.api.GetTrackListUseCase
import com.practicum.playlistmaker.domain.model.Resource

class TrackRepositoryImpl(
    private val getTrackNetworkClient: GetTrackNetworkClient
) : GetTrackListUseCase {
    override fun getTrackList(expression: String): Resource<List<Track>> {
        val response = getTrackNetworkClient.getTrackList(expression)

        return if (response is GetTrackListResponse) {
            val result = GetTrackMapper.map(response.trackDtoList)
            Resource.Success(result)
        } else {
            Resource.Error("Произошла ошибка сети")
        }
    }
}