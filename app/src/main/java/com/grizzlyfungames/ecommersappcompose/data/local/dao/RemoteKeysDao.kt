package com.grizzlyfungames.ecommersappcompose.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.grizzlyfungames.ecommersappcompose.data.local.entity.RemoteKeysEntity

@Dao
interface RemoteKeysDao {
    // Сохраняем ключи пагинации при получении новых данных из сети
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(remoteKey: List<RemoteKeysEntity>)

    // Достаем ключи для конкретного товара, чтобы узнать,
    // какая страница была до него и какая будет после
    @Query("SELECT * FROM remote_keys WHERE productId = :id")
    suspend fun getRemoteKeysForProduct(id: Int): RemoteKeysEntity?

    // Очищаем таблицу (например, при Refresh/Pull-to-refresh)
    @Query("DELETE FROM remote_keys")
    suspend fun clearRemoteKeys()
}