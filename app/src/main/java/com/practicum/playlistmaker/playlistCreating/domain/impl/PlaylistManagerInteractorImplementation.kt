package com.practicum.playlistmaker.playlistCreating.domain.impl

import com.practicum.playlistmaker.player.domain.entity.Track
import com.practicum.playlistmaker.playlistCreating.domain.api.PlaylistManagerInteractor
import com.practicum.playlistmaker.playlistCreating.domain.api.PlaylistManagerRepository
import com.practicum.playlistmaker.playlistCreating.domain.entity.Playlist
import com.practicum.playlistmaker.utilities.Resource
import kotlinx.coroutines.flow.Flow

class PlaylistManagerInteractorImplementation(private val playlistManagerRepository: PlaylistManagerRepository) :
    PlaylistManagerInteractor {

    override suspend fun addPlaylist(playlist: Playlist): Long {
        return playlistManagerRepository.addPlaylist(playlist)
    }

    override suspend fun deletePlaylist(playlist: Playlist) {
        playlistManagerRepository.deletePlaylist(playlist)
    }

    override fun getPlaylistsFromTable(): Flow<List<Playlist>> {
        return playlistManagerRepository.getPlaylistsFromTable()
    }

    override suspend fun addTrackToPlaylist(track: Track, playlistId: Playlist) {
        playlistManagerRepository.addTrackToPlaylist(track, playlistId)
    }

}