package com.practicum.playlistmaker.roomTables.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.practicum.playlistmaker.roomTables.tables.PlaylistsTableEntity

@Dao
interface ManagePlaylistDaoInterface {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlaylist(playlistsTableEntity: PlaylistsTableEntity): Long

    @Query("SELECT * FROM playlists_table")
    suspend fun getPlaylists(): List<PlaylistsTableEntity>

    @Query("UPDATE playlists_table SET trackQuantity = :trackQuantity WHERE playlistId = :playlistId")
    suspend fun updateTracksQuantityInPlaylist(trackQuantity: Int, playlistId: Int)

    @Delete(entity = PlaylistsTableEntity::class)
    suspend fun deletePlaylist(playlistsTableEntity: PlaylistsTableEntity)

    @Update
    suspend fun editPlaylist(playlist: PlaylistsTableEntity)

    @Query("SELECT * FROM playlists_table WHERE playlistId = :playlistId")
    suspend fun getPlaylist(playlistId: Int): PlaylistsTableEntity
}