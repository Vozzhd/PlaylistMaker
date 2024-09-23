package com.practicum.playlistmaker.roomTables.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.practicum.playlistmaker.roomTables.tables.TracksInPlaylistsTableEntity

@Dao
interface ManageTracksDaoInterface {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertTrackToPlaylist(track: TracksInPlaylistsTableEntity): Long

    @Delete(entity = TracksInPlaylistsTableEntity::class)
    suspend fun deleteTrackFromPlaylist(tracksInPlaylistsTableEntity: TracksInPlaylistsTableEntity)

    @Query("DELETE FROM tracks_in_playlist_table WHERE trackId =:trackId")
    suspend fun deleteTrackFromPlaylistsTable(trackId: Int)
}