package com.practicum.playlistmaker.roomTables.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.practicum.playlistmaker.roomTables.tables.FavoriteTableEntity

@Dao
interface FavoriteTracksDaoInterface {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTrackToFavoriteTable(favoriteTableEntity: FavoriteTableEntity)

    @Delete(entity = FavoriteTableEntity::class)
    suspend fun deleteTrackFromFavoriteTable(favoriteTableEntity: FavoriteTableEntity)

    @Query("SELECT * FROM favorite_table")
    suspend fun getFavoriteTracksTable(): List<FavoriteTableEntity>

    @Query("SELECT trackId FROM favorite_table")
    suspend fun getFavoriteIDsFromFavoriteTable(): List<Int>
}