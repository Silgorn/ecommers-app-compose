package com.grizzlyfungames.ecommersappcompose.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.grizzlyfungames.ecommersappcompose.data.api.ProductApi
import com.grizzlyfungames.ecommersappcompose.data.local.AppDatabase
import com.grizzlyfungames.ecommersappcompose.data.paging.ProductRemoteMediator
import com.grizzlyfungames.ecommersappcompose.domain.repository.ProductRepository
import com.grizzlyfungames.ecommersappcompose.domain.util.SortOrder
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val api: ProductApi,
    private val db: AppDatabase
) : ProductRepository {

    @OptIn(ExperimentalPagingApi::class)
    override fun getProducts(query: String?, category: String?, sort: SortOrder) = Pager(
        config = PagingConfig(pageSize = 20),
        remoteMediator = ProductRemoteMediator(
            api,
            db,
            searchQuery = query,
            categoryName = category
        ),
        pagingSourceFactory = {
            when (sort) {
                SortOrder.DEFAULT -> db.productDao().getProductsDefault(category, query)
                SortOrder.LOW_PRICE -> db.productDao().getProductsPriceAsc(category, query)
                SortOrder.HIGH_PRICE -> db.productDao().getProductsPriceDesc(category, query)
            }
        }
    ).flow
}