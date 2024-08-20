package com.practicum.playlistmaker.playlistCreating.domain.impl

import  android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Environment
import androidx.core.net.toUri
import com.practicum.playlistmaker.player.domain.entity.Track
import com.practicum.playlistmaker.playlistCreating.data.db.converters.PlaylistDbConverter
import com.practicum.playlistmaker.playlistCreating.data.db.converters.TrackDbConverterForPlaylist
import com.practicum.playlistmaker.playlistCreating.domain.api.PlaylistManagerRepository
import com.practicum.playlistmaker.playlistCreating.domain.entity.Playlist
import com.practicum.playlistmaker.utilities.AppDatabase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.File
import java.io.FileOutputStream

class PlaylistManagerRepositoryImplementation(
    private val appDatabase: AppDatabase,
    private val context: Context,
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

    private fun savePlaylistCoverImage(playlist: Playlist): Uri {
        val filePath = File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), "playlist")
        if (!filePath.exists()) {
            filePath.mkdirs()
        }

        val file = File(filePath, "${playlist.name}.jpg")
        val inputStream = playlist.sourceOfPlaylistCoverImage?.let {
            context.contentResolver.openInputStream(it)
        }
        val outputStream = FileOutputStream(file)
        BitmapFactory
            .decodeStream(inputStream)
            .compress(Bitmap.CompressFormat.JPEG, 30, outputStream)
        return file.toUri()
    }

    override suspend fun addTrackToPlaylist(track: Track, playlistId: Int): Long {
        val operationStatus = appDatabase.daoInterface()
            .insertTrackToPlaylist(trackDbConverterForPlaylist.map(track, playlistId))
        return operationStatus
    }
}