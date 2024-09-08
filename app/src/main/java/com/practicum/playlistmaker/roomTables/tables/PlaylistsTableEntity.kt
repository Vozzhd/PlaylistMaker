package com.practicum.playlistmaker.roomTables.tables

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "playlists_table")
data class PlaylistsTableEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int?,
    val name: String,
    val description: String,
    val trackQuantity: Int,
    val listOfTrackIDs : String,
    val SourceOfPlaylistCoverImage: String
)