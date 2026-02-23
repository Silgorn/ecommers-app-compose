package com.grizzlyfungames.ecommersappcompose.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.grizzlyfungames.ecommersappcompose.data.local.entity.FavoritesEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoritesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(entity: FavoritesEntity)

    @Query("DELETE FROM favorites WHERE id = :productId")
    suspend fun deleteFavorite(productId: Int)

    @Query("SELECT * FROM favorites")
    fun getAllFavorites(): Flow<List<FavoritesEntity>>

    @Query("SELECT id FROM favorites")
    fun getFavoriteIds(): Flow<List<Int>>
}