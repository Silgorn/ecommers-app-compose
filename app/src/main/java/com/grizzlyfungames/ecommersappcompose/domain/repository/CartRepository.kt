package com.grizzlyfungames.ecommersappcompose.domain.repository

import com.grizzlyfungames.ecommersappcompose.data.local.entity.CartEntity
import com.grizzlyfungames.ecommersappcompose.data.local.entity.ProductEntity
import kotlinx.coroutines.flow.Flow

interface CartRepository {
    fun getAllCartItems(): Flow<List<CartEntity>>

    fun getCartItemIds(): Flow<List<Int>>

    fun isInCartFlow(productId: Int): Flow<Boolean>

    fun getTotalCount(): Flow<Int>

    suspend fun addToCart(product: ProductEntity)

    suspend fun removeFromCart(productId: Int)

    suspend fun incrementQuantity(productId: Int)

    suspend fun decrementQuantity(productId: Int)
}