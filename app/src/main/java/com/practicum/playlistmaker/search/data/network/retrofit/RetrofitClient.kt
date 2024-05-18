package com.practicum.playlistmaker.search.data.network.retrofit

import com.practicum.playlistmaker.search.data.network.TrackGettingApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private val client: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("https://itunes.apple.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    val api: TrackGettingApi by lazy {
        client.create(TrackGettingApi::class.java)
    }
}