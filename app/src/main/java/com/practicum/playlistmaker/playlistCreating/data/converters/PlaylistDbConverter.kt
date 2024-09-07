package com.practicum.playlistmaker.playlistCreating.data.converters

import android.net.Uri
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.practicum.playlistmaker.playlistCreating.data.entity.PlaylistDbEntity
import com.practicum.playlistmaker.playlistCreating.domain.entity.Playlist


class PlaylistDbConverter {

    var builder: GsonBuilder = GsonBuilder()
    var gson: Gson = builder.create()

    fun map(playlist: Playlist): PlaylistDbEntity {
        return PlaylistDbEntity(
            playlist.id,
            playlist.name,
            playlist.description,
            playlist.trackQuantity,
            listOfTrackIDs = gson.toJson(playlist.listOfTrackIDs),
            playlist.sourceOfPlaylistCoverImage.toString()
        )
    }

    fun map(playlistDbEntity: PlaylistDbEntity): Playlist {
        return Playlist(
            playlistDbEntity.id,
            playlistDbEntity.name,
            playlistDbEntity.description,
            playlistDbEntity.trackQuantity,
            listOfTrackIDs = gson.fromJson(playlistDbEntity.listOfTrackIDs, object : TypeToken<List<String>>() {}.type),
            Uri.parse(playlistDbEntity.SourceOfPlaylistCoverImage)
        )
    }
}