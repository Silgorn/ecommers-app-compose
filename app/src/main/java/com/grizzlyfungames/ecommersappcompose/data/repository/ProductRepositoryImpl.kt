package com.grizzlyfungames.ecommersappcompose.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.grizzlyfungames.ecommersappcompose.data.api.ProductApi
import com.grizzlyfungames.ecommersappcompose.data.local.AppDatabase
import com.grizzlyfungames.ecommersappcompose.data.paging.ProductRemoteMediator
import com.grizzlyfungames.ecommersappcompose.domain.repository.ProductRepository
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val api: ProductApi,
    private val db: AppDatabase
) : ProductRepository {

    @OptIn(ExperimentalPagingApi::class)
    override fun getProducts(query: String?, category: String?) = Pager(
        config = PagingConfig(pageSize = 20),
        remoteMediator = ProductRemoteMediator(
            api,
            db,
            searchQuery = query,
            categoryName = category
        ),
        pagingSourceFactory = {
            db.productDao().getFilteredProducts(
                categorySlug = category,
                query = query
            )
        }
    ).flow
}