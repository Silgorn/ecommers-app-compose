package com.grizzlyfungames.ecommersappcompose.data.api

import com.grizzlyfungames.ecommersappcompose.data.api.dto.CategoryDto
import com.grizzlyfungames.ecommersappcompose.data.api.dto.ProductResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ProductApi {

    @GET("products")
    suspend fun getProducts(
        @Query("limit") limit: Int = 20,
        @Query("skip") skip: Int = 0
    ): ProductResponse

    @GET("products/search")
    suspend fun searchProducts(
        @Query("q") query: String,
        @Query("limit") limit: Int = 20,
        @Query("skip") skip: Int = 0
    ): ProductResponse

    @GET("products/categories")
    suspend fun getCategories(): List<CategoryDto>

    @GET("products/category/{categoryName}")
    suspend fun getProductsByCategory(
        @Path("categoryName") categoryName: String,
        @Query("limit") limit: Int = 20,
        @Query("skip") skip: Int = 0
    ): ProductResponse

    companion object {
        const val BASE_URL = "https://dummyjson.com/"
    }
}