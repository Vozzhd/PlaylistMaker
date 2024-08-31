package com.practicum.playlistmaker.utilities

import androidx.room.Database
import androidx.room.RoomDatabase
import com.practicum.playlistmaker.mediaLibrary.favorite.data.db.dao.FavoriteDaoInterface
import com.practicum.playlistmaker.mediaLibrary.favorite.data.db.entity.TrackEntity
import com.practicum.playlistmaker.playlistCreating.data.entity.PlaylistDbEntity
import com.practicum.playlistmaker.playlistCreating.data.entity.TrackInPlaylistEntity

@Database(version = 1, entities = [TrackEntity::class, PlaylistDbEntity::class, TrackInPlaylistEntity::class], exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun daoInterface(): FavoriteDaoInterface
}