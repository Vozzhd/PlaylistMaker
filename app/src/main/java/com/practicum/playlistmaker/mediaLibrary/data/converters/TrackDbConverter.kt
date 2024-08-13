package com.practicum.playlistmaker.mediaLibrary.data.converters

import com.practicum.playlistmaker.mediaLibrary.data.db.entity.TrackEntity
import com.practicum.playlistmaker.player.domain.entity.Track
import com.practicum.playlistmaker.search.data.dto.TrackDto

class TrackDbConverter {
    fun map(track: Track): TrackEntity {
        return TrackEntity(
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

fun map (track: TrackEntity) : Track {
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
        track.previewUrl
    )
}
}
