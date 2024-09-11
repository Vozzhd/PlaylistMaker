package com.practicum.playlistmaker.playlistManage.domain.api

import com.practicum.playlistmaker.player.domain.entity.Track
import com.practicum.playlistmaker.playlistManage.domain.entity.Playlist
import kotlinx.coroutines.flow.Flow

interface PlaylistManagerRepository {
    fun getPlaylistsFromTable(): Flow<List<Playlist>>
    suspend fun getTracksInPlaylist(playlistId: Int): List<Track>
    suspend fun addPlaylist(playlist: Playlist): Long
    suspend fun deletePlaylist(playlist: Playlist)
    suspend fun addTrackToPlaylist(track: Track, playlistId: Playlist)
    suspend fun deleteTrackFromPlaylist(track: Track, playlist: Playlist)
}