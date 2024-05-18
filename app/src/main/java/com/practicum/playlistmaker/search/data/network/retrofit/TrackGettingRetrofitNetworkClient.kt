package com.practicum.playlistmaker.search.data.network.retrofit

import com.practicum.playlistmaker.search.data.network.NetworkResponse
import com.practicum.playlistmaker.search.data.network.TrackGettingNetworkClient

class TrackGettingRetrofitNetworkClient : TrackGettingNetworkClient {
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