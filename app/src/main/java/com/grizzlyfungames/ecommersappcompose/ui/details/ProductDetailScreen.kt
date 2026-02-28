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
    val product by viewModel.productState.collectAsState()

    Scaffold(
        bottomBar = {
            product?.let { currentProduct ->
                AddToCartBottomBar(onAddToCartClick = { viewModel.addToCart(currentProduct) })
            }
        }
    ) { padding ->
        product?.let { currentProduct ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .verticalScroll(rememberScrollState())
                    .background(Color.White)
            ) {
                ProductImageCarousel(
                    images = currentProduct.images,
                    onBackClick = onBackClick,
                    isFavorite = currentProduct.isFavorite,
                    onFavoriteClick = {
                        viewModel.toggleFavorite(currentProduct)
                    },
                    onShareClick = { /* TODO */ }
                )
                ProductDetailsSection(
                    title = currentProduct.title,
                    rating = currentProduct.rating,
                    price = currentProduct.price,
                    discountPercentage = currentProduct.discountPercentage,
                    description = currentProduct.description
                )
            }
        }
    }
}