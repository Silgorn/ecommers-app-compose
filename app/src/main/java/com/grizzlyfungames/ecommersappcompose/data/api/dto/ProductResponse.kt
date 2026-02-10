package com.grizzlyfungames.ecommersappcompose.data.api.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProductResponse(
    @SerialName("products") val results: List<ProductDto>,
    @SerialName("total") val total: Int,
    @SerialName("skip") val skip: Int,
    @SerialName("limit") val limit: Int
)
