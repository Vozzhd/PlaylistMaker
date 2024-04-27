package com.practicum.playlistmaker.data.network

import com.practicum.playlistmaker.data.dto.GetTrackListResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface GetTrackApi {
    @GET("/search?entity=song")
    fun search(@Query("term") text: String): Call<GetTrackListResponse>
}