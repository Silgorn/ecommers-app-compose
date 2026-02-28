package com.grizzlyfungames.ecommersappcompose.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.grizzlyfungames.ecommersappcompose.data.local.entity.ProductEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProducts(products: List<ProductEntity>)

    @Query("SELECT * FROM products WHERE id = :id")
    suspend fun getProductById(id: Int): ProductEntity?

    @Query("DELETE FROM products")
    suspend fun clearAll()

    @Query(
        """
        SELECT products.*, (favorites.id IS NOT NULL) AS isFavorite 
        FROM products 
        LEFT JOIN favorites ON products.id = favorites.id
        WHERE (:category IS NULL OR products.category = :category) 
        AND (:query IS NULL OR products.title LIKE '%' || :query || '%')
        ORDER BY products.id ASC
    """
    )
    fun getProductsDefault(category: String?, query: String?): PagingSource<Int, ProductEntity>

    @Query(
        """
        SELECT products.*, (favorites.id IS NOT NULL) AS isFavorite 
        FROM products 
        LEFT JOIN favorites ON products.id = favorites.id
        WHERE (:category IS NULL OR products.category = :category) 
        AND (:query IS NULL OR products.title LIKE '%' || :query || '%')
        ORDER BY products.price ASC
    """
    )
    fun getProductsPriceAsc(category: String?, query: String?): PagingSource<Int, ProductEntity>

    @Query(
        """
        SELECT products.*, (favorites.id IS NOT NULL) AS isFavorite 
        FROM products 
        LEFT JOIN favorites ON products.id = favorites.id
        WHERE (:category IS NULL OR products.category = :category) 
        AND (:query IS NULL OR products.title LIKE '%' || :query || '%')
        ORDER BY products.price DESC
    """
    )
    fun getProductsPriceDesc(category: String?, query: String?): PagingSource<Int, ProductEntity>

    @Query(
        """
    SELECT products.*, 1 AS isFavorite 
    FROM products 
    INNER JOIN favorites ON products.id = favorites.id
"""
    )
    fun getFavoriteProductsSource(): PagingSource<Int, ProductEntity>

    @Query(
        """
    SELECT *, 
    (SELECT EXISTS(SELECT 1 FROM favorites WHERE favorites.id = products.id)) AS isFavorite
    FROM products 
    WHERE id = :id
"""
    )
    fun getProductByIdFlow(id: Int): Flow<ProductEntity?>
}
