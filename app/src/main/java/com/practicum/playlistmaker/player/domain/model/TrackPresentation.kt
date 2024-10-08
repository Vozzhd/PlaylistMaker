package com.practicum.playlistmaker.player.domain.model

import java.io.Serializable

data class TrackPresentation(
    val trackName: String,
    val artistName: String,
    val trackDuration: String,
    val artworkUrl100: String,
    val trackId: String,
    val collectionName: String,
    val releaseDate: String,
    val primaryGenreName: String,
    val country: String,
    val previewUrl: String,
    val artworkUrl512: String,
    val isFavorite:Boolean
) : Serializable