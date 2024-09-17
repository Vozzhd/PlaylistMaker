package com.practicum.playlistmaker.roomTables.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.practicum.playlistmaker.roomTables.crossTables.PlaylistWithTracks
import com.practicum.playlistmaker.roomTables.crossTables.PlaylistsTracksInPlaylistsCrossReferenceTable
import com.practicum.playlistmaker.roomTables.crossTables.TrackWithPlaylists

@Dao
interface CrossReferenceTablesDaoInterface {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTrackToCrossTable(entity: PlaylistsTracksInPlaylistsCrossReferenceTable)

    @Transaction
    @Query("SELECT * FROM playlists_table WHERE playlistId = :playlistId")
    suspend fun getTracksInPlaylist(playlistId: Int): PlaylistWithTracks

    @Transaction
    @Query("SELECT * FROM tracks_in_playlist_table WHERE trackId = :trackId")
    suspend fun getPlaylistsOfTrack(trackId: Int): TrackWithPlaylists

    @Query("DELETE FROM cross_reference_track_playlist WHERE trackId =:trackId AND playlistId =:playlistId")
    suspend fun deleteTrackFromCrossRefTable(trackId: Int,playlistId: Int)

}