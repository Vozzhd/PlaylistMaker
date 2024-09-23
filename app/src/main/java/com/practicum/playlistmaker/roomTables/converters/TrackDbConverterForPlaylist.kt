package com.practicum.playlistmaker.roomTables.converters

import com.practicum.playlistmaker.player.domain.entity.Track
import com.practicum.playlistmaker.roomTables.tables.TracksInPlaylistsTableEntity

class TrackDbConverterForPlaylist {

    fun map(track: Track): TracksInPlaylistsTableEntity {
        return TracksInPlaylistsTableEntity(
            track.trackId,
            track.artworkUrl100,
            track.trackName,
            track.artistName,
            track.collectionName,
            track.releaseDate,
            track.primaryGenreName,
            track.country,
            track.trackTimeMillis,
            track.previewUrl,
            track.isFavorite
        )
    }


    fun map(track: TracksInPlaylistsTableEntity): Track {
        return Track(
            track.trackName,
            track.artistName,
            track.trackTimeMillis,
            track.artworkUrl100,
            track.trackId,
            track.collectionName,
            track.releaseDate,
            track.primaryGenreName,
            track.country,
            track.previewUrl,
            false
        )
    }
}