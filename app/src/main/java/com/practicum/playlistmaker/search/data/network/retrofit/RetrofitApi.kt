package com.practicum.playlistmaker.search.data.network.retrofit
import com.practicum.playlistmaker.search.data.network.TrackResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitApi {
    @GET("/search?entity=song")
    fun search(@Query("term") text: String): Call<TrackResponse>
}