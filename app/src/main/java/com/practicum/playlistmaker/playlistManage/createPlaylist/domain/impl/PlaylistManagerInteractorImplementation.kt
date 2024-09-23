package com.practicum.playlistmaker.playlistManage.createPlaylist.domain.impl

import com.practicum.playlistmaker.player.domain.entity.Track
import com.practicum.playlistmaker.playlistManage.createPlaylist.domain.api.PlaylistManagerInteractor
import com.practicum.playlistmaker.playlistManage.createPlaylist.domain.api.PlaylistManagerRepository
import com.practicum.playlistmaker.playlistManage.createPlaylist.domain.entity.Playlist
import kotlinx.coroutines.flow.Flow

class PlaylistManagerInteractorImplementation(private val playlistManagerRepository: PlaylistManagerRepository) :
    PlaylistManagerInteractor {

    override suspend fun addPlaylist(playlist: Playlist) {
        playlistManagerRepository.addPlaylist(playlist)
    }

    override suspend fun deletePlaylist(playlist: Playlist) {
        playlistManagerRepository.deletePlaylist(playlist)
    }

    override fun getPlaylistsFromTable(): Flow<List<Playlist>> {
        return playlistManagerRepository.getPlaylistsFromTable()
    }

    override suspend fun editPlaylist(playlist: Playlist) {
        playlistManagerRepository.editPlaylist(playlist)
    }

    override suspend fun getTracksInPlaylist(playlistId: Int): List<Track> {
        return playlistManagerRepository.getTracksInPlaylist(playlistId)
    }

    override suspend fun getPlaylist(playlistId: Int): Playlist {
        return playlistManagerRepository.getPlaylist(playlistId)
    }

    override suspend fun addTrackToPlaylist(track: Track, playlistId: Playlist) {
        playlistManagerRepository.addTrackToPlaylist(track, playlistId)
    }

    override suspend fun deleteTrackFromPlaylist(track: Track, playlist: Playlist) {
        playlistManagerRepository.deleteTrackFromPlaylist(track, playlist)
    }

}