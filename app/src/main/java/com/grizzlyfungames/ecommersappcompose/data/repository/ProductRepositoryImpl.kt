package com.grizzlyfungames.ecommersappcompose.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.grizzlyfungames.ecommersappcompose.data.api.ProductApi
import com.grizzlyfungames.ecommersappcompose.data.local.AppDatabase
import com.grizzlyfungames.ecommersappcompose.data.paging.ProductRemoteMediator
import com.grizzlyfungames.ecommersappcompose.domain.repository.ProductRepository

class ProductRepositoryImpl(
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
            when {
                // Если выбрана категория — берем только её
                !category.isNullOrBlank() -> db.productDao().getProductsByCategory(category)

                // Если есть поисковый запрос — ищем по нему
                // Передаем query без знаков %, так как они добавлены в DAO
                !query.isNullOrBlank() -> db.productDao().searchProductsInDb(query)

                // Иначе отдаем все товары
                else -> db.productDao().getAllProducts()
            }
        }
    ).flow
}