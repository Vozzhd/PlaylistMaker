package com.practicum.playlistmaker.roomTables.crossTables

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.practicum.playlistmaker.player.domain.entity.Track
import com.practicum.playlistmaker.roomTables.tables.PlaylistsTableEntity

data class TrackWithPlaylists(
    @Embedded val track: Track,
    @Relation(
        parentColumn = "trackId",
        entityColumn = "playlistId",
        associateBy = Junction(PlaylistsTracksInPlaylistsCrossReferenceTable::class)
    )
    val playlists: List<PlaylistsTableEntity>
)