package com.grizzlyfungames.ecommersappcompose.data.api.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CategoryDto(
    @SerialName("slug") val slug: String,
    @SerialName("name") val name: String
)
