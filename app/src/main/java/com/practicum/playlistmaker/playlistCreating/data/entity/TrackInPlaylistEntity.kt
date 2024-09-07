package com.practicum.playlistmaker.playlistCreating.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tracks_in_playlist_table")
data class TrackInPlaylistEntity(
    @PrimaryKey
    val trackId: String,
    val artworkUrl100: String,
    val trackName: String,
    val artistName: String,
    val collectionName: String,
    val releaseDate: String,
    val primaryGenreName: String,
    val country: String,
    val trackTimeMillis: String,
    val previewUrl: String,
    val isFavorite: Boolean
)