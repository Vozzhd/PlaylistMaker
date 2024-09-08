package com.practicum.playlistmaker.roomTables.converters

import com.practicum.playlistmaker.roomTables.tables.FavoriteTableEntity
import com.practicum.playlistmaker.player.domain.entity.Track

class TrackDbConverter {
    fun map(track: Track): FavoriteTableEntity {
        return FavoriteTableEntity(
            track.trackId,
            track.artworkUrl100,
            track.trackName,
            track.artistName,
            track.collectionName,
            track.releaseDate,
            track.primaryGenreName,
            track.country,
            track.trackTimeMillis,
            track.previewUrl
        )
    }

fun map (track: FavoriteTableEntity) : Track {
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
