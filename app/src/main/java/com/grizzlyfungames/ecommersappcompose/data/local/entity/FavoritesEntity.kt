package com.grizzlyfungames.ecommersappcompose.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorites")
data class FavoritesEntity(
    @PrimaryKey
    val id: Int,
    val title: String,
    val price: Double,
    val discountPercentage: Double,
    val thumbnail: String,
)
