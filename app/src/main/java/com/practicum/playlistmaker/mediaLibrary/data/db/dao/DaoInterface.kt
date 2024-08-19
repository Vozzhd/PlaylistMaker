package com.practicum.playlistmaker.mediaLibrary.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.practicum.playlistmaker.mediaLibrary.data.db.entity.TrackEntity

@Dao
interface DaoInterface {

    //Manage favorite tracks

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTrackToFavoriteTable(trackEntity: TrackEntity)

    @Delete(entity = TrackEntity::class)
    suspend fun deleteTrackFromFavoriteTable(trackEntity: TrackEntity)

    @Query("SELECT * FROM favorite_table")
    suspend fun getFavoriteTracksTable(): List<TrackEntity>

    @Query("SELECT trackId FROM favorite_table")
    suspend fun getFavoriteIDsFromFavoriteTable(): List<String>

    //Manage playlist's

//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    suspend fun insertPlaylist(playlistEntity: PlaylistEntity): Long
//
//    @Query("SELECT * FROM playlists_table")
//    suspend fun getPlaylists(): List<PlaylistEntity>
//
//    @Query("UPDATE playlists_table SET trackQuantity = :trackQuantity WHERE id = :playlistId")
//    suspend fun updateTracksQuantityInPlaylist(trackQuantity: Int, playlistId: Int)
//
//    @Delete(entity = PlaylistEntity::class)
//    suspend fun deletePlaylist(playlistEntity: PlaylistEntity)

    //Manage tracks inside playlist

}