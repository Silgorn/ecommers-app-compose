package com.grizzlyfungames.ecommersappcompose.data.mapper

import com.grizzlyfungames.ecommersappcompose.data.api.dto.ProductDto
import com.grizzlyfungames.ecommersappcompose.data.local.entity.ProductEntity

fun ProductDto.toEntity(): ProductEntity {
    return ProductEntity(
        id = id,
        title = title,
        description = description,
        category = category,
        price = price,
        discountPercentage = discountPercentage,
        rating = rating,
        stock = stock,
        thumbnail = thumbnail,
        images = images
    )
}