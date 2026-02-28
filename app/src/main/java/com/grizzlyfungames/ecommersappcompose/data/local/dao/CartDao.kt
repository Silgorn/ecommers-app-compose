package com.grizzlyfungames.ecommersappcompose.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.grizzlyfungames.ecommersappcompose.data.local.entity.CartEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CartDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCartItems(entity: CartEntity)

    @Query("DELETE FROM cart WHERE id = :productId")
    suspend fun deleteCartItem(productId: Int)

    @Query("SELECT * FROM cart")
    fun getAllCartItems(): Flow<List<CartEntity>>

    @Query("SELECT id FROM cart")
    fun getCartItemIds(): Flow<List<Int>>

    @Query("SELECT EXISTS(SELECT 1 FROM cart WHERE id = :id)")
    fun isInCartFlow(id: Int): Flow<Boolean>

    @Query("UPDATE cart SET quantity = quantity + 1 WHERE id = :productId")
    suspend fun incrementQuantity(productId: Int)

    @Query("UPDATE cart SET quantity = CASE WHEN quantity > 1 THEN quantity - 1 ELSE 1 END WHERE id = :productId")
    suspend fun decrementQuantity(productId: Int)

    @Query("SELECT SUM(quantity) FROM cart")
    fun getTotalCount(): Flow<Int?>
}