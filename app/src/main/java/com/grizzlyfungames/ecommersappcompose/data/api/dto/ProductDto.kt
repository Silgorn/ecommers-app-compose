package com.grizzlyfungames.ecommersappcompose.data.api.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProductDto(
    @SerialName("id") val id: Int,
    @SerialName("title") val title: String,
    @SerialName("description") val description: String,
    @SerialName("category") val category: String,
    @SerialName("price") val price: Double,
    @SerialName("discountPercentage") val discountPercentage: Double,
    @SerialName("rating") val rating: Double,
    @SerialName("stock") val stock: Int,
    @SerialName("thumbnail") val thumbnail: String
)