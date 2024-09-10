package com.practicum.playlistmaker.playlistCreating.domain.api

import com.practicum.playlistmaker.player.domain.entity.Track
import com.practicum.playlistmaker.playlistCreating.domain.entity.Playlist
import com.practicum.playlistmaker.roomTables.crossTables.PlaylistWithTracks
import com.practicum.playlistmaker.utilities.Resource
import kotlinx.coroutines.flow.Flow

interface PlaylistManagerInteractor {

    fun getPlaylistsFromTable(): Flow<List<Playlist>>
    suspend fun getTracksInPlaylist(playlistId: Int): List<Track>
    suspend fun addPlaylist(playlist: Playlist): Long
    suspend fun deletePlaylist(playlist: Playlist)
    suspend fun addTrackToPlaylist(track: Track, playlistId: Playlist)
}