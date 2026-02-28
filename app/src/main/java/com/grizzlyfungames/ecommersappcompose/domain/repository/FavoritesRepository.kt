package com.grizzlyfungames.ecommersappcompose.domain.repository

import com.grizzlyfungames.ecommersappcompose.data.local.entity.FavoritesEntity
import com.grizzlyfungames.ecommersappcompose.data.local.entity.ProductEntity
import kotlinx.coroutines.flow.Flow

interface FavoritesRepository {
    suspend fun toggleFavorite(product: ProductEntity, isCurrentlyFavorite: Boolean)

    fun getAllFavorites(): Flow<List<FavoritesEntity>>

    fun getFavoriteIds(): Flow<List<Int>>

    fun isFavoriteFlow(productId: Int): Flow<Boolean>
}