package com.practicum.playlistmaker.data.network

import com.practicum.playlistmaker.data.dto.NetworkResponse
import com.practicum.playlistmaker.data.repository.GetTrackNetworkClient

class GetTrackRetrofitNetworkClient : GetTrackNetworkClient {
    override fun getTrackList(expression: String): NetworkResponse {
        return try {
            val response = RetrofitClient.api.search(expression).execute()
            val networkResponse = response.body() ?: NetworkResponse()
            networkResponse.apply { resultCode = response.code() }

        } catch (ex: Exception) {
            NetworkResponse().apply { resultCode = 400 }
        }
    }
}