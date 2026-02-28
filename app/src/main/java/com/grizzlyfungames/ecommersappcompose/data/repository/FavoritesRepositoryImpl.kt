package com.grizzlyfungames.ecommersappcompose.data.repository

import com.grizzlyfungames.ecommersappcompose.data.local.dao.FavoritesDao
import com.grizzlyfungames.ecommersappcompose.data.local.entity.FavoritesEntity
import com.grizzlyfungames.ecommersappcompose.data.local.entity.ProductEntity
import com.grizzlyfungames.ecommersappcompose.domain.repository.FavoritesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FavoritesRepositoryImpl @Inject constructor(
    private val favoritesDao: FavoritesDao
) : FavoritesRepository {

    override suspend fun toggleFavorite(product: ProductEntity, isCurrentlyFavorite: Boolean) {
        if (isCurrentlyFavorite) {
            favoritesDao.deleteFavorite(product.id)
        } else {
            val entity = FavoritesEntity(
                id = product.id,
                title = product.title,
                price = product.price,
                discountPercentage = product.discountPercentage,
                thumbnail = product.thumbnail,
            )
            favoritesDao.insertFavorite(entity)
        }
    }

    override fun getAllFavorites(): Flow<List<FavoritesEntity>> {
        return favoritesDao.getAllFavorites()
    }

    override fun getFavoriteIds(): Flow<List<Int>> {
        return favoritesDao.getFavoriteIds()
    }

    override fun isFavoriteFlow(productId: Int): Flow<Boolean> {
        return favoritesDao.isFavoriteFlow(productId)
    }
}