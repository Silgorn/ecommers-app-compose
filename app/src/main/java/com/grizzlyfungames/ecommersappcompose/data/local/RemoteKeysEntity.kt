package com.grizzlyfungames.ecommersappcompose.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "remote_keys")
data class RemoteKeysEntity(
    @PrimaryKey
    val productId: Int,
    val prevKey: Int?,
    val nextKey: Int?
)
