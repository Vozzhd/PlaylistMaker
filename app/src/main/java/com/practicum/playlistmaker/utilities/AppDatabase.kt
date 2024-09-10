package com.practicum.playlistmaker.utilities

import androidx.room.Database
import androidx.room.RoomDatabase
import com.practicum.playlistmaker.roomTables.crossTables.PlaylistsTracksInPlaylistsCrossReferenceTable
import com.practicum.playlistmaker.roomTables.dao.DaoInterface
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
    abstract fun daoInterface(): DaoInterface
}