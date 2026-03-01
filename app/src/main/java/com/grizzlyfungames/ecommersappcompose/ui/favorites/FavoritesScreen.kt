package com.grizzlyfungames.ecommersappcompose.ui.favorites

import android.annotation.SuppressLint
import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import com.grizzlyfungames.ecommersappcompose.ui.MainViewModel
import com.grizzlyfungames.ecommersappcompose.ui.products.components.ProductGrid
import com.grizzlyfungames.ecommersappcompose.ui.topbar.AppTopBar

@Composable
fun FavoriteScreen(
    viewModel: FavoritesViewModel = hiltViewModel(),
    @SuppressLint("ContextCastToActivity") mainViewModel: MainViewModel = hiltViewModel(LocalContext.current as ComponentActivity),
    onProductClick: (Int) -> Unit
) {
    val favoriteItems = viewModel.favoriteProducts.collectAsLazyPagingItems()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        AppTopBar("Favorite")

        ProductGrid(
            products = favoriteItems,
            gridState = rememberLazyGridState(),
            onProductClick = onProductClick,
            onFavoriteToggle = { product ->
                viewModel.onFavoriteToggle(product)
                val message = if (product.isFavorite) {
                    "Removed from Favorites"
                } else {
                    "${product.title} added to Favorites"
                }
                mainViewModel.showMessage(message)
            }
        )
    }
}