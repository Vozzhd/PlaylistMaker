package com.practicum.playlistmaker.roomTables.crossTables

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.practicum.playlistmaker.player.domain.entity.Track
import com.practicum.playlistmaker.playlistCreating.domain.entity.Playlist
import com.practicum.playlistmaker.roomTables.tables.PlaylistsTableEntity
import com.practicum.playlistmaker.roomTables.tables.TracksInPlaylistsTableEntity

data class PlaylistWithTracks(
    @Embedded val playlist : PlaylistsTableEntity,
    @Relation(
        parentColumn = "playlistId",
        entityColumn = "trackId",
        associateBy = Junction(PlaylistsTracksInPlaylistsCrossReferenceTable::class)
    )
    val tracks: List<TracksInPlaylistsTableEntity>
)