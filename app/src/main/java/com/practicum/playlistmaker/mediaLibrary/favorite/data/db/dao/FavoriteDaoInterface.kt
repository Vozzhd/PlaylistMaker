package com.practicum.playlistmaker.mediaLibrary.favorite.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.practicum.playlistmaker.mediaLibrary.favorite.data.db.entity.TrackEntity
import com.practicum.playlistmaker.playlistCreating.data.entity.PlaylistDbEntity
import com.practicum.playlistmaker.playlistCreating.data.entity.TrackInPlaylistEntity

@Dao
interface FavoriteDaoInterface {

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

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlaylist(playlistDbEntity: PlaylistDbEntity): Long

    @Query("SELECT * FROM playlists_table")
    suspend fun getPlaylists(): List<PlaylistDbEntity>

    @Query("UPDATE playlists_table SET trackQuantity = :trackQuantity WHERE id = :playlistId")
    suspend fun updateTracksQuantityInPlaylist(trackQuantity: Int, playlistId: Int)

    @Delete(entity = PlaylistDbEntity::class)
    suspend fun deletePlaylist(playlistDbEntity: PlaylistDbEntity)

    //Manage tracks inside playlist
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertTrackToPlaylist(track: TrackInPlaylistEntity) : Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertTrackToPlaylistNew(track: TrackEntity) : Long

    @Delete(entity = TrackEntity::class)
    suspend fun deleteTrackFromPlaylist(trackEntity: TrackEntity)

//    @Query("SELECT * FROM tracks_in_playlist_table")
//    suspend fun getIDsFromTracksInPlaylists(albumId : Int) : List<TrackInPlaylistEntity>

}