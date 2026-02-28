package com.grizzlyfungames.ecommersappcompose.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.grizzlyfungames.ecommersappcompose.data.api.ProductApi
import com.grizzlyfungames.ecommersappcompose.data.local.AppDatabase
import com.grizzlyfungames.ecommersappcompose.data.local.entity.ProductEntity
import com.grizzlyfungames.ecommersappcompose.data.paging.ProductRemoteMediator
import com.grizzlyfungames.ecommersappcompose.domain.repository.ProductRepository
import com.grizzlyfungames.ecommersappcompose.domain.util.SortOrder
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val api: ProductApi,
    private val db: AppDatabase,

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

    override fun getFavoriteProductsFlow(): Flow<PagingData<ProductEntity>> {
        return Pager(
            config = PagingConfig(pageSize = 20),
            pagingSourceFactory = { db.productDao().getFavoriteProductsSource() }
        ).flow
    }

    override fun getProductByIdFlow(id: Int): Flow<ProductEntity?> {
        return db.productDao().getProductByIdFlow(id)
    }
}