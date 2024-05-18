package com.practicum.playlistmaker.search.data.network

import com.practicum.playlistmaker.search.data.dto.TrackGettingListResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface TrackGettingApi {
    @GET("/search?entity=song")
    fun search(@Query("term") text: String): Call<TrackGettingListResponse>
}