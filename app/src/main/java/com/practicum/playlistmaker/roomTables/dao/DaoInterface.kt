package com.practicum.playlistmaker.roomTables.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.practicum.playlistmaker.player.domain.entity.Track
import com.practicum.playlistmaker.playlistManage.createPlaylist.domain.entity.Playlist
import com.practicum.playlistmaker.roomTables.crossTables.PlaylistWithTracks
import com.practicum.playlistmaker.roomTables.crossTables.PlaylistsTracksInPlaylistsCrossReferenceTable
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
    suspend fun getFavoriteIDsFromFavoriteTable(): List<Int>

    //Manage playlist's

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlaylist(playlistsTableEntity: PlaylistsTableEntity): Long

    @Query("SELECT * FROM playlists_table")
    suspend fun getPlaylists(): List<PlaylistsTableEntity>

    @Query("UPDATE playlists_table SET trackQuantity = :trackQuantity WHERE playlistId = :playlistId")
    suspend fun updateTracksQuantityInPlaylist(trackQuantity: Int, playlistId: Int)

    @Delete(entity = PlaylistsTableEntity::class)
    suspend fun deletePlaylist(playlistsTableEntity: PlaylistsTableEntity)

    //Manage tracks inside playlist
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertTrackToPlaylist(track: TracksInPlaylistsTableEntity): Long

    @Delete(entity = TracksInPlaylistsTableEntity::class)
    suspend fun deleteTrackFromPlaylist(tracksInPlaylistsTableEntity: TracksInPlaylistsTableEntity)

    //Cross-reference tables functions
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTrackToCrossTable(entity: PlaylistsTracksInPlaylistsCrossReferenceTable)

    @Transaction
    @Query("SELECT * FROM playlists_table WHERE playlistId = :playlistId")
    suspend fun getTracksInPlaylist(playlistId: Int): PlaylistWithTracks

    @Query("DELETE FROM cross_reference_track_playlist WHERE trackId =:trackId")
    suspend fun deleteTrackFromCrossRefTable(trackId: Int)

    @Update
    suspend fun editPlaylist(playlist: PlaylistsTableEntity)

}