package com.grizzlyfungames.ecommersappcompose.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.grizzlyfungames.ecommersappcompose.data.local.entity.CategoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategories(categories: List<CategoryEntity>)

    // Исправлено имя таблицы на "category" (как в твоем @Entity)
    @Query("SELECT * FROM category")
    fun getAllCategories(): Flow<List<CategoryEntity>>

    // Добавляем этот метод для разового получения данных в репозитории
    @Query("SELECT * FROM category")
    suspend fun getAllCategoriesOnce(): List<CategoryEntity>

    @Query("DELETE FROM category")
    suspend fun clearAll()
}