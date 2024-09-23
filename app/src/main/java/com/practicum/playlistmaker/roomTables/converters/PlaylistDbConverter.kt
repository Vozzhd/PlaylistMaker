package com.practicum.playlistmaker.roomTables.converters

import android.net.Uri
import com.practicum.playlistmaker.roomTables.tables.PlaylistsTableEntity
import com.practicum.playlistmaker.playlistManage.createPlaylist.domain.entity.Playlist


class PlaylistDbConverter {

    fun map(playlist: Playlist): PlaylistsTableEntity {
        return PlaylistsTableEntity(
            playlist.playlistId,
            playlist.name,
            playlist.description,
            playlist.trackQuantity,
            playlist.sourceOfPlaylistCoverImage.toString()
        )
    }

    fun map(playlistsTableEntity: PlaylistsTableEntity): Playlist {
        return Playlist(
            playlistsTableEntity.playlistId,
            playlistsTableEntity.name,
            playlistsTableEntity.description,
            playlistsTableEntity.trackQuantity,
            Uri.parse(playlistsTableEntity.sourceOfPlaylistCoverImage)
        )
    }
}