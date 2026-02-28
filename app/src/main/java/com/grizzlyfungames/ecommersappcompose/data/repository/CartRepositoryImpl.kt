package com.grizzlyfungames.ecommersappcompose.data.repository

import com.grizzlyfungames.ecommersappcompose.data.local.dao.CartDao
import com.grizzlyfungames.ecommersappcompose.data.local.entity.CartEntity
import com.grizzlyfungames.ecommersappcompose.data.local.entity.ProductEntity
import com.grizzlyfungames.ecommersappcompose.domain.repository.CartRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CartRepositoryImpl @Inject constructor(
    private val cartDao: CartDao
) : CartRepository {

    override fun getAllCartItems(): Flow<List<CartEntity>> = cartDao.getAllCartItems()

    override fun getCartItemIds(): Flow<List<Int>> = cartDao.getCartItemIds()

    override fun isInCartFlow(productId: Int): Flow<Boolean> = cartDao.isInCartFlow(productId)

    override fun getTotalCount(): Flow<Int> = cartDao.getTotalCount().map { it ?: 0 }

    override suspend fun addToCart(product: ProductEntity) {

        val isAlreadyInCart = cartDao.isInCartFlow(product.id).first()

        if (isAlreadyInCart) {
            cartDao.incrementQuantity(product.id)
        } else {
            cartDao.insertCartItems(
                CartEntity(
                    id = product.id,
                    title = product.title,
                    price = product.price,
                    thumbnail = product.thumbnail,
                    discountPercentage = product.discountPercentage,
                    quantity = 1,
                )
            )
        }
    }

    override suspend fun removeFromCart(productId: Int) {
        cartDao.deleteCartItem(productId)
    }

    override suspend fun incrementQuantity(productId: Int) {
        cartDao.incrementQuantity(productId)
    }

    override suspend fun decrementQuantity(productId: Int) {
        cartDao.decrementQuantity(productId)
    }
}