package com.grizzlyfungames.ecommersappcompose.ui.details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import com.grizzlyfungames.ecommersappcompose.ui.details.components.AddToCartBottomBar
import com.grizzlyfungames.ecommersappcompose.ui.details.components.ProductDetailsSection
import com.grizzlyfungames.ecommersappcompose.ui.details.components.ProductImageCarousel

@Composable
fun ProductDetailScreen(
    onBackClick: () -> Unit,
    viewModel: ProductDetailViewModel = hiltViewModel()
) {
    val product by viewModel.product.collectAsState()

    Scaffold(
        bottomBar = {
            product?.let {
                AddToCartBottomBar(onAddToCartClick = { /* TODO */ })
            }
        }
    ) { padding ->
        product?.let { entity ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .verticalScroll(rememberScrollState())
                    .background(Color.White)
            ) {
                // 1. Блок с картинками и верхними кнопками
                ProductImageCarousel(
                    images = entity.images,
                    onBackClick = onBackClick,
                    onFavoriteClick = { /* TODO */ },
                    onShareClick = { /* TODO */ }
                )

                // 2. Блок с информацией о товаре
                ProductDetailsSection(
                    title = entity.title,
                    rating = entity.rating,
                    price = entity.price,
                    discountPercentage = entity.discountPercentage,
                    description = entity.description
                )
            }
        }
    }
}