package com.grizzlyfungames.ecommersappcompose.data.repository

import androidx.room.withTransaction
import com.grizzlyfungames.ecommersappcompose.data.api.ProductApi
import com.grizzlyfungames.ecommersappcompose.data.local.AppDatabase
import com.grizzlyfungames.ecommersappcompose.data.local.entity.CategoryEntity
import com.grizzlyfungames.ecommersappcompose.data.mapper.toEntity
import com.grizzlyfungames.ecommersappcompose.domain.repository.CategoryRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class CategoryRepositoryImpl @Inject constructor(
    private val api: ProductApi,
    private val db: AppDatabase
) : CategoryRepository {

    override fun getCategories(): Flow<List<CategoryEntity>> = flow {
        // 1. Сначала эмитим текущий кэш
        val cached = db.categoryDao().getAllCategoriesOnce()
        emit(cached)

        // 2. Делаем запрос в сеть в блоке try-catch
        try {
            val remote = api.getCategories()

            db.withTransaction {
                db.categoryDao().clearAll()
                // Маппим DTO в Entity
                db.categoryDao().insertCategories(remote.map { it.toEntity() })
            }

            // 3. После успешного обновления БД эмитим новые данные
            emit(db.categoryDao().getAllCategoriesOnce())
        } catch (e: Exception) {
            // Ошибка (нет сети и т.д.) — ничего не делаем,
            // так как первый emit уже отдал кэшированные данные
        }
    }.flowOn(Dispatchers.IO) // Гарантируем работу в фоновом потоке
}