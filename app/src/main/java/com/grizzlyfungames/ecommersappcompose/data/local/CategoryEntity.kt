package com.grizzlyfungames.ecommersappcompose.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "categories")
data class CategoryEntity(
    @PrimaryKey
    val slug: String,
    val name: String,
)