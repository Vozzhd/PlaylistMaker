package com.practicum.playlistmaker.playlistCreating.domain.impl

import com.practicum.playlistmaker.player.domain.entity.Track
import com.practicum.playlistmaker.roomTables.converters.PlaylistDbConverter
import com.practicum.playlistmaker.roomTables.converters.TrackDbConverterForPlaylist
import com.practicum.playlistmaker.playlistCreating.domain.api.PlaylistManagerRepository
import com.practicum.playlistmaker.playlistCreating.domain.entity.Playlist
import com.practicum.playlistmaker.roomTables.crossTables.PlaylistWithTracks
import com.practicum.playlistmaker.roomTables.crossTables.PlaylistsTracksInPlaylistsCrossReferenceTable
import com.practicum.playlistmaker.utilities.AppDatabase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class PlaylistManagerRepositoryImplementation(
    private val appDatabase: AppDatabase,
    private val trackDbConverterForPlaylist: TrackDbConverterForPlaylist,
    private val playlistDbConverter: PlaylistDbConverter
) : PlaylistManagerRepository {

    override fun getPlaylistsFromTable(): Flow<List<Playlist>> = flow {
        val playlists = appDatabase.daoInterface().getPlaylists()
        val convertedPlaylist = playlists.map { playlist -> playlistDbConverter.map(playlist) }
        emit(convertedPlaylist)
    }

    override suspend fun getTracksInPlaylist(playlistId: Int): List<Track> {
        val tracksInPlaylistData = appDatabase.daoInterface().getTracksInPlaylist(playlistId)
        val mappedTrackList = tracksInPlaylistData.tracks.map { track -> trackDbConverterForPlaylist.map(track)}
        return mappedTrackList
    }

    override suspend fun addPlaylist(playlist: Playlist): Long {
        return appDatabase.daoInterface().insertPlaylist(playlistDbConverter.map(playlist))
    }

    override suspend fun deletePlaylist(playlist: Playlist) {
        appDatabase.daoInterface().deletePlaylist(playlistDbConverter.map(playlist))
    }

    override suspend fun addTrackToPlaylist(track: Track, playlist: Playlist)  {

        val updatedQuantity = playlist.trackQuantity + 1

        appDatabase.daoInterface().updateTracksQuantityInPlaylist(updatedQuantity, playlist.playlistId)
        appDatabase.daoInterface().insertTrackToPlaylist(trackDbConverterForPlaylist.map(track))
        appDatabase.daoInterface().insertTrackToCrossTable(PlaylistsTracksInPlaylistsCrossReferenceTable(track.trackId,playlist.playlistId))
    }
}