package com.grizzlyfungames.ecommersappcompose.domain.repository

import com.grizzlyfungames.ecommersappcompose.data.local.entity.CategoryEntity
import kotlinx.coroutines.flow.Flow

interface CategoryRepository {
    fun getCategories(): Flow<List<CategoryEntity>>
}