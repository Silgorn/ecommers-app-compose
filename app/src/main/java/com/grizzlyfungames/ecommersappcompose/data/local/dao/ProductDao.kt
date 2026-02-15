package com.grizzlyfungames.ecommersappcompose.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.grizzlyfungames.ecommersappcompose.data.local.entity.ProductEntity

@Dao
interface ProductDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProducts(products: List<ProductEntity>)

    @Query("SELECT * FROM products")
    fun getAllProducts(): PagingSource<Int, ProductEntity>

    @Query(
        """
    SELECT * FROM products 
    WHERE (:categorySlug IS NULL OR category = :categorySlug) 
    AND (:query IS NULL OR title LIKE '%' || :query || '%')
"""
    )
    fun getFilteredProducts(
        categorySlug: String?,
        query: String?
    ): PagingSource<Int, ProductEntity>

    @Query("SELECT * FROM products WHERE category = :categorySlug")
    fun getProductsByCategory(categorySlug: String): PagingSource<Int, ProductEntity>

    @Query("SELECT * FROM products WHERE title LIKE '%' || :query || '%' OR description LIKE '%' || :query || '%'")
    fun searchProductsInDb(query: String): PagingSource<Int, ProductEntity>

    @Query("DELETE FROM products")
    suspend fun clearAll()
}