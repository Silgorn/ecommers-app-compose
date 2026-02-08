package com.grizzlyfungames.ecommersappcompose.data.api

import com.grizzlyfungames.ecommersappcompose.data.api.dto.CategoryDto
import com.grizzlyfungames.ecommersappcompose.data.api.dto.ProductsResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ProductApi {

    @GET("products")
    suspend fun getProducts(
        @Query("limit") limit: Int = 20,
        @Query("skip") skip: Int = 0
    ): ProductsResponse

    @GET("products/search")
    suspend fun searchProducts(
        @Query("q") query: String
    ): ProductsResponse

    @GET("products/categories")
    suspend fun getCategories(): List<CategoryDto>

    @GET("products/category/{categoryName}")
    suspend fun getProductsByCategory(
        @Path("categoryName") categoryName: String,
        @Query("limit") limit: Int = 20,
        @Query("skip") skip: Int = 0
    ): ProductsResponse

    companion object {
        const val BASE_URL = "https://dummyjson.com/"
    }
}