package com.practicum.playlistmaker.recyclerView

import java.io.Serializable

data class Track(
    val trackName: String,
    val artistName: String,
    val trackTimeMillis: String,
    val artworkUrl100: String,
    val trackId: Int,
    val collectionName: String,
    val releaseDate: String,
    val primaryGenreName: String,
    val country: String
) : Serializable {
    val artworkUrl512 get() = artworkUrl100.replaceAfterLast('/', "512x512bb.jpg")
}
