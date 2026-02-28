package com.grizzlyfungames.ecommersappcompose.domain.model

data class UserProfile(
    val name: String,
    val email: String,
    val avatarUrl: String,
    val ordersCount: Int,
    val bonusPoints: Int
)
