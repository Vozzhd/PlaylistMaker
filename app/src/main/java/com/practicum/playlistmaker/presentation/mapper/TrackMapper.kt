package com.practicum.playlistmaker.presentation.mapper

import com.practicum.playlistmaker.domain.entity.Track
import com.practicum.playlistmaker.presentation.model.TrackPresentation
import java.text.SimpleDateFormat
import java.util.Locale
object TrackMapper {
    private val dateFormat by lazy { SimpleDateFormat("mm:ss", Locale.getDefault()) }
    fun map(track: Track): TrackPresentation {
        return TrackPresentation(
            trackName = track.trackName,
            artistName = track.artistName,
            trackDuration = dateFormat.format(track.trackTimeMillis.toInt()),
            artworkUrl100 = track.artworkUrl100,
            trackId = track.trackId,
            collectionName = track.collectionName,
            releaseDate = track.releaseDate.substring(0, 4),
            primaryGenreName = track.primaryGenreName,
            country = track.country,
            previewUrl = track.previewUrl,
            artworkUrl512 =  track.artworkUrl100.replaceAfterLast('/', "512x512bb.jpg")
        )
    }
    //SimpleDateFormat("mm:ss", Locale.getDefault()).format(track.trackTimeMillis)
}