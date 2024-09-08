package com.practicum.playlistmaker.playlistCreating.domain.impl

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.practicum.playlistmaker.player.domain.entity.Track
import com.practicum.playlistmaker.roomTables.converters.PlaylistDbConverter
import com.practicum.playlistmaker.roomTables.converters.TrackDbConverterForPlaylist
import com.practicum.playlistmaker.playlistCreating.domain.api.PlaylistManagerRepository
import com.practicum.playlistmaker.playlistCreating.domain.entity.Playlist
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

    override suspend fun addPlaylist(playlist: Playlist): Long {
        return appDatabase.daoInterface().insertPlaylist(playlistDbConverter.map(playlist))
    }

    override suspend fun deletePlaylist(playlist: Playlist) {
        appDatabase.daoInterface().deletePlaylist(playlistDbConverter.map(playlist))
    }

    override suspend fun addTrackToPlaylist(track: Track, playlist: Playlist)  {

        val updatedTrackList = playlist.listOfTrackIDs + track.trackId
        val updatedQuantity = playlist.trackQuantity + 1

        val gsonBuilder = GsonBuilder()
        val gson: Gson = gsonBuilder.create()

        val listOfTrackIDs = gson.toJson(updatedTrackList)

        appDatabase.daoInterface().updateListOfTrackIDs(listOfTrackIDs, playlist.id)
        appDatabase.daoInterface().updateTracksQuantityInPlaylist(updatedQuantity, playlist.id)
        appDatabase.daoInterface().insertTrackToPlaylist(trackDbConverterForPlaylist.map(track))

    }
}