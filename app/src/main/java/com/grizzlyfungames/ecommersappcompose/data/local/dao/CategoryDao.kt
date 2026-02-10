package com.grizzlyfungames.ecommersappcompose.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.grizzlyfungames.ecommersappcompose.data.local.entity.CategoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDao {

    // Сохраняем все категории, которые прислал сервер
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategories(categories: List<CategoryEntity>)

    // Получаем список категорий для отображения в "чипсах" (фильтрах)
    // Используем Flow, чтобы UI обновлялся сам
    @Query("SELECT * FROM categories")
    fun getAllCategories(): Flow<List<CategoryEntity>>

    // Очистка при необходимости
    @Query("DELETE FROM categories")
    suspend fun clearAll()
}