package com.practicum.playlistmaker.roomTables.converters

import android.net.Uri
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.practicum.playlistmaker.roomTables.tables.PlaylistsTableEntity
import com.practicum.playlistmaker.playlistCreating.domain.entity.Playlist


class PlaylistDbConverter {

    var builder: GsonBuilder = GsonBuilder()
    var gson: Gson = builder.create()

    fun map(playlist: Playlist): PlaylistsTableEntity {
        return PlaylistsTableEntity(
            playlist.id,
            playlist.name,
            playlist.description,
            playlist.trackQuantity,
            listOfTrackIDs = gson.toJson(playlist.listOfTrackIDs),
            playlist.sourceOfPlaylistCoverImage.toString()
        )
    }

    fun map(playlistsTableEntity: PlaylistsTableEntity): Playlist {
        return Playlist(
            playlistsTableEntity.id,
            playlistsTableEntity.name,
            playlistsTableEntity.description,
            playlistsTableEntity.trackQuantity,
            listOfTrackIDs = gson.fromJson(playlistsTableEntity.listOfTrackIDs, object : TypeToken<List<String>>() {}.type),
            Uri.parse(playlistsTableEntity.SourceOfPlaylistCoverImage)
        )
    }
}