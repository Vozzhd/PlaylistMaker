package com.practicum.playlistmaker.playlistCreating.data.db.converters

import android.net.Uri
import com.practicum.playlistmaker.playlistCreating.data.db.entity.PlaylistDbEntity
import com.practicum.playlistmaker.playlistCreating.domain.entity.Playlist

class PlaylistDbConverter {
    fun map(playlist: Playlist): PlaylistDbEntity {
        return PlaylistDbEntity(
            playlist.id,
            playlist.name,
            playlist.description,
            playlist.trackQuantity,
            playlist.listOfTrackIDs,
            playlist.sourceOfPlaylistCoverImage.toString()
        )
    }

    fun map(playlistDbEntity: PlaylistDbEntity) : Playlist{
        return Playlist(
            playlistDbEntity.id,
            playlistDbEntity.name,
            playlistDbEntity.description,
            playlistDbEntity.trackQuantity,
            playlistDbEntity.listOfTrackIDs,
            Uri.parse(playlistDbEntity.SourceOfPlaylistCoverImage)
        )
    }
}