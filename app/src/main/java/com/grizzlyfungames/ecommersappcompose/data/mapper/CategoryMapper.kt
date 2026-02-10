package com.grizzlyfungames.ecommersappcompose.data.mapper

import com.grizzlyfungames.ecommersappcompose.data.api.dto.CategoryDto
import com.grizzlyfungames.ecommersappcompose.data.local.entity.CategoryEntity

fun CategoryDto.toEntity(): CategoryEntity {
    return CategoryEntity(
        slug = slug,
        name = name
    )
}