package com.grizzlyfungames.ecommersappcompose.domain.repository

import androidx.paging.PagingData
import com.grizzlyfungames.ecommersappcompose.data.local.entity.ProductEntity
import com.grizzlyfungames.ecommersappcompose.domain.util.SortOrder
import kotlinx.coroutines.flow.Flow

interface ProductRepository {
    fun getProducts(
        query: String?,
        category: String?,
        sort: SortOrder
    ): Flow<PagingData<ProductEntity>>
}