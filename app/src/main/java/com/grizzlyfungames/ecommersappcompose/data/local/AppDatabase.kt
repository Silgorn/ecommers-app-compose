package com.grizzlyfungames.ecommersappcompose.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.grizzlyfungames.ecommersappcompose.data.local.converters.ProductConverters
import com.grizzlyfungames.ecommersappcompose.data.local.dao.CategoryDao
import com.grizzlyfungames.ecommersappcompose.data.local.dao.ProductDao
import com.grizzlyfungames.ecommersappcompose.data.local.dao.RemoteKeysDao
import com.grizzlyfungames.ecommersappcompose.data.local.entity.CategoryEntity
import com.grizzlyfungames.ecommersappcompose.data.local.entity.ProductEntity
import com.grizzlyfungames.ecommersappcompose.data.local.entity.RemoteKeysEntity

@Database(
    entities = [ProductEntity::class, CategoryEntity::class, RemoteKeysEntity::class],
    version = 2,
    exportSchema = false
)
@TypeConverters(ProductConverters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDao
    abstract fun categoryDao(): CategoryDao
    abstract fun remoteKeysDao(): RemoteKeysDao
}