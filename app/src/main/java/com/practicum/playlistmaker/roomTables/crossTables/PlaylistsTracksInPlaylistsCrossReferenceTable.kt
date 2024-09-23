package com.practicum.playlistmaker.roomTables.crossTables

import androidx.room.Entity

@Entity(tableName = "cross_reference_track_playlist", primaryKeys = ["trackId","playlistId"])
data class PlaylistsTracksInPlaylistsCrossReferenceTable(
    val trackId: Int,
    val playlistId: Int
)