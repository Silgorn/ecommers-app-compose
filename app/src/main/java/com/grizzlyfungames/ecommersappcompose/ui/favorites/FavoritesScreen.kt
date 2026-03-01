package com.grizzlyfungames.ecommersappcompose.ui.favorites

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import com.grizzlyfungames.ecommersappcompose.ui.products.components.ProductGrid
import com.grizzlyfungames.ecommersappcompose.ui.topbar.AppTopBar

@Composable
fun FavoriteScreen(
    viewModel: FavoritesViewModel = hiltViewModel(),
    onProductClick: (Int) -> Unit
) {
    val favoriteItems = viewModel.favoriteProducts.collectAsLazyPagingItems()

    Column(modifier = Modifier.fillMaxSize()) {
        AppTopBar("Favorite")

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