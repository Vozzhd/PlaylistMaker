package com.practicum.playlistmaker.roomTables.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.practicum.playlistmaker.roomTables.tables.FavoriteTableEntity
import com.practicum.playlistmaker.roomTables.tables.PlaylistsTableEntity
import com.practicum.playlistmaker.roomTables.tables.TracksInPlaylistsTableEntity

@Dao
interface DaoInterface {

    //Manage favorite tracks

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTrackToFavoriteTable(favoriteTableEntity: FavoriteTableEntity)

    @Delete(entity = FavoriteTableEntity::class)
    suspend fun deleteTrackFromFavoriteTable(favoriteTableEntity: FavoriteTableEntity)

    @Query("SELECT * FROM favorite_table")
    suspend fun getFavoriteTracksTable(): List<FavoriteTableEntity>

    @Query("SELECT trackId FROM favorite_table")
    suspend fun getFavoriteIDsFromFavoriteTable(): List<String>

    //Manage playlist's

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlaylist(playlistsTableEntity: PlaylistsTableEntity): Long

    @Query("SELECT * FROM playlists_table")
    suspend fun getPlaylists(): List<PlaylistsTableEntity>

    @Query("UPDATE playlists_table SET trackQuantity = :trackQuantity WHERE id = :playlistId")
    suspend fun updateTracksQuantityInPlaylist(trackQuantity: Int, playlistId: Int?)

    @Query("UPDATE playlists_table SET listOfTrackIDs =:listOfTrackIDs WHERE id =:playlistId")
    suspend fun updateListOfTrackIDs(listOfTrackIDs: String, playlistId: Int?)

    @Delete(entity = PlaylistsTableEntity::class)
    suspend fun deletePlaylist(playlistsTableEntity: PlaylistsTableEntity)

    //Manage tracks inside playlist
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertTrackToPlaylist(track: TracksInPlaylistsTableEntity): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertTrackToPlaylistNew(track: FavoriteTableEntity): Long

    @Delete(entity = FavoriteTableEntity::class)
    suspend fun deleteTrackFromPlaylist(favoriteTableEntity: FavoriteTableEntity)

}