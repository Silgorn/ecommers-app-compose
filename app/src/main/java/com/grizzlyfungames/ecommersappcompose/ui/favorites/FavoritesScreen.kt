package com.grizzlyfungames.ecommersappcompose.ui.favorites

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import com.grizzlyfungames.ecommersappcompose.ui.products.components.ProductGrid

@Composable
fun FavoriteScreen(
    viewModel: FavoritesViewModel = hiltViewModel(),
    onProductClick: (Int) -> Unit
) {
    val favoriteItems = viewModel.favoriteProducts.collectAsLazyPagingItems()

    Column(modifier = Modifier.fillMaxSize()) {
        Text(
            text = "Favorite",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(start = 16.dp, top = 16.dp, bottom = 8.dp)
        )

        ProductGrid(
            products = favoriteItems,
            gridState = rememberLazyGridState(),
            onProductClick = onProductClick,
            onFavoriteToggle = { product ->
                viewModel.onFavoriteToggle(product)
            }
        )
    }
}