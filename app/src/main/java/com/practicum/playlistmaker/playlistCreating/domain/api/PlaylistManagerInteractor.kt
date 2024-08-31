package com.practicum.playlistmaker.playlistCreating.domain.api

import com.practicum.playlistmaker.player.domain.entity.Track
import com.practicum.playlistmaker.playlistCreating.domain.entity.Playlist
import kotlinx.coroutines.flow.Flow

interface PlaylistManagerInteractor {

    fun getPlaylistsFromTable(): Flow<List<Playlist>>

    suspend fun addPlaylist(playlist: Playlist): Long
    suspend fun deletePlaylist(playlist: Playlist)
    suspend fun addTrackToPlaylist(track: Track, playlistId: Int?): Long
}