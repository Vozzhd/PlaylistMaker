package com.practicum.playlistmaker.playlistManage.createPlaylist.domain.api

import com.practicum.playlistmaker.player.domain.entity.Track
import com.practicum.playlistmaker.playlistManage.createPlaylist.domain.entity.Playlist
import kotlinx.coroutines.flow.Flow

interface PlaylistManagerInteractor {

    fun getPlaylistsFromTable(): Flow<List<Playlist>>
    suspend fun getTracksInPlaylist(playlistId: Int): List<Track>
    suspend fun getPlaylist(playlistId: Int): Playlist

    suspend fun addPlaylist(playlist: Playlist)
    suspend fun addTrackToPlaylist(track: Track, playlistId: Playlist)

    suspend fun editPlaylist(playlist: Playlist)

    suspend fun deletePlaylist(playlist: Playlist)
    suspend fun deleteTrackFromPlaylist(track: Track, playlist: Playlist)
}