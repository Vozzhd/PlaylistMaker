package com.practicum.playlistmaker.playlistManage.createPlaylist.domain.impl

import com.practicum.playlistmaker.player.domain.entity.Track
import com.practicum.playlistmaker.roomTables.converters.PlaylistDbConverter
import com.practicum.playlistmaker.roomTables.converters.TrackDbConverterForPlaylist
import com.practicum.playlistmaker.playlistManage.createPlaylist.domain.api.PlaylistManagerRepository
import com.practicum.playlistmaker.playlistManage.createPlaylist.domain.entity.Playlist
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
        val playlists = appDatabase.managePlaylistDaoInterface().getPlaylists()
        val convertedPlaylist = playlists.map { playlist -> playlistDbConverter.map(playlist) }
        emit(convertedPlaylist)
    }

    override suspend fun editPlaylist(playlist: Playlist) {
        val convertedPlaylist = playlistDbConverter.map(playlist)
        appDatabase.managePlaylistDaoInterface().editPlaylist(convertedPlaylist)
    }

    override suspend fun getTracksInPlaylist(playlistId: Int): List<Track> {
        val tracksInPlaylistData = appDatabase.crossReferenceTablesDaoInterface().getTracksInPlaylist(playlistId)
        val mappedTrackList =
            tracksInPlaylistData.tracks.map { track -> trackDbConverterForPlaylist.map(track) }
        return mappedTrackList
    }

    override suspend fun addPlaylist(playlist: Playlist): Long {
        return appDatabase.managePlaylistDaoInterface().insertPlaylist(playlistDbConverter.map(playlist))
    }

    override suspend fun deletePlaylist(playlist: Playlist) {
        appDatabase.managePlaylistDaoInterface().deletePlaylist(playlistDbConverter.map(playlist))
    }

    override suspend fun addTrackToPlaylist(track: Track, playlist: Playlist) {
        appDatabase.manageTracksDaoInterface().insertTrackToPlaylist(trackDbConverterForPlaylist.map(track))
        appDatabase.crossReferenceTablesDaoInterface().insertTrackToCrossTable(
            PlaylistsTracksInPlaylistsCrossReferenceTable(
                track.trackId,
                playlist.playlistId
            )
        )
        appDatabase.managePlaylistDaoInterface().updateTracksQuantityInPlaylist(
            appDatabase.crossReferenceTablesDaoInterface().getTracksInPlaylist(playlist.playlistId).tracks.size,
            playlist.playlistId
        )

    }

    override suspend fun deleteTrackFromPlaylist(track: Track, playlist: Playlist) {

        appDatabase.crossReferenceTablesDaoInterface().deleteTrackFromCrossRefTable(track.trackId, playlist.playlistId)

        val trackWithPlaylist = appDatabase.crossReferenceTablesDaoInterface().getPlaylistsOfTrack(track.trackId)

        val mappedPlaylists = trackWithPlaylist.playlists.map { playlist -> playlistDbConverter.map(playlist) }
        if (mappedPlaylists.isEmpty()) {
            appDatabase.manageTracksDaoInterface().deleteTrackFromPlaylist(trackDbConverterForPlaylist.map(track))
        }

        appDatabase.managePlaylistDaoInterface().updateTracksQuantityInPlaylist(
            appDatabase.crossReferenceTablesDaoInterface().getTracksInPlaylist(playlist.playlistId).tracks.size,
            playlist.playlistId
        )
    }

    override suspend fun getPlaylist(playlistId: Int): Playlist {
        return playlistDbConverter.map(appDatabase.managePlaylistDaoInterface().getPlaylist(playlistId))
    }
}