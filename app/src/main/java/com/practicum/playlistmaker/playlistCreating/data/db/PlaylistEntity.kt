package com.practicum.playlistmaker.playlistCreating.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "playlists_table")
 data class PlaylistEntity(
    @PrimaryKey
    val id: Int?,
    val name: String,
    val description: String,
    val trackQuantity: Int,
    val SourceOfPlaylistCoverImage: String
)