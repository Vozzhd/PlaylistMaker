package com.practicum.playlistmaker.utilities

import androidx.room.Database
import androidx.room.RoomDatabase
import com.practicum.playlistmaker.roomTables.crossTables.PlaylistsTracksInPlaylistsCrossReferenceTable
import com.practicum.playlistmaker.roomTables.dao.CrossReferenceTablesDaoInterface
import com.practicum.playlistmaker.roomTables.dao.FavoriteTracksDaoInterface
import com.practicum.playlistmaker.roomTables.dao.ManagePlaylistDaoInterface
import com.practicum.playlistmaker.roomTables.dao.ManageTracksDaoInterface
import com.practicum.playlistmaker.roomTables.tables.FavoriteTableEntity
import com.practicum.playlistmaker.roomTables.tables.PlaylistsTableEntity
import com.practicum.playlistmaker.roomTables.tables.TracksInPlaylistsTableEntity

@Database(
    version = 1, entities = [
        FavoriteTableEntity::class,
        PlaylistsTableEntity::class,
        TracksInPlaylistsTableEntity::class,
        PlaylistsTracksInPlaylistsCrossReferenceTable::class
    ],
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun crossReferenceTablesDaoInterface(): CrossReferenceTablesDaoInterface
    abstract fun favoriteTracksDaoInterface(): FavoriteTracksDaoInterface
    abstract fun managePlaylistDaoInterface(): ManagePlaylistDaoInterface
    abstract fun manageTracksDaoInterface(): ManageTracksDaoInterface
}