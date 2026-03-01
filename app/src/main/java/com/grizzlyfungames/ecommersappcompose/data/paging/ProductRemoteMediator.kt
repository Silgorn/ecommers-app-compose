package com.grizzlyfungames.ecommersappcompose.data.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.grizzlyfungames.ecommersappcompose.data.api.ProductApi
import com.grizzlyfungames.ecommersappcompose.data.local.AppDatabase
import com.grizzlyfungames.ecommersappcompose.data.local.entity.ProductEntity
import com.grizzlyfungames.ecommersappcompose.data.local.entity.RemoteKeysEntity
import com.grizzlyfungames.ecommersappcompose.data.mapper.toEntity
import com.grizzlyfungames.ecommersappcompose.domain.util.SortOrder

@OptIn(ExperimentalPagingApi::class)
class ProductRemoteMediator(
    private val api: ProductApi,
    private val db: AppDatabase,
    private val searchQuery: String? = null,
    private val categoryName: String? = null,
    private val sortOrder: SortOrder
) : RemoteMediator<Int, ProductEntity>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, ProductEntity>
    ): MediatorResult {
        return try {
            val skip = when (loadType) {
                LoadType.REFRESH -> 0
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    val lastItem = state.lastItemOrNull()
                        ?: return MediatorResult.Success(endOfPaginationReached = false)

                    val remoteKey = db.remoteKeysDao().getRemoteKeysForProduct(lastItem.id)
                    val nextKey = remoteKey?.nextKey
                        ?: return MediatorResult.Success(endOfPaginationReached = remoteKey != null)
                    nextKey
                }
            }

            val limit = state.config.pageSize

            val sortBy = when (sortOrder) {
                SortOrder.LOW_PRICE, SortOrder.HIGH_PRICE -> "price"
                else -> null
            }
            val order = when (sortOrder) {
                SortOrder.LOW_PRICE -> "asc"
                SortOrder.HIGH_PRICE -> "desc"
                else -> null
            }

            val response = when {
                !searchQuery.isNullOrBlank() -> {
                    api.searchProducts(
                        query = searchQuery,
                        limit = limit,
                        skip = skip,
                        sortBy = sortBy,
                        order = order
                    )
                }

                !categoryName.isNullOrBlank() -> {
                    api.getProductsByCategory(
                        categoryName = categoryName,
                        limit = limit,
                        skip = skip,
                        sortBy = sortBy,
                        order = order
                    )
                }

                else -> {
                    api.getProducts(
                        limit = limit,
                        skip = skip,
                        sortBy = sortBy,
                        order = order
                    )
                }
            }

            val products = response.results
            val isEndOfList = response.skip + products.size >= response.total

            db.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    db.remoteKeysDao().clearRemoteKeys()
                    db.productDao().clearAll()
                }

                val keys = products.map { product ->
                    RemoteKeysEntity(
                        productId = product.id,
                        prevKey = if (skip == 0) null else skip - limit,
                        nextKey = if (isEndOfList) null else skip + limit
                    )
                }

                db.remoteKeysDao().insertAll(keys)
                db.productDao().insertProducts(products.map { it.toEntity() })
            }

            MediatorResult.Success(endOfPaginationReached = isEndOfList)
        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }
}