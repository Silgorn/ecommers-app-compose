package com.grizzlyfungames.ecommersappcompose.ui.products

import android.annotation.SuppressLint
import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.grizzlyfungames.ecommersappcompose.ui.MainViewModel
import com.grizzlyfungames.ecommersappcompose.ui.products.components.CategoryChips
import com.grizzlyfungames.ecommersappcompose.ui.products.components.ProductGrid
import com.grizzlyfungames.ecommersappcompose.ui.products.components.SearchBar
import com.grizzlyfungames.ecommersappcompose.ui.topbar.AppTopBar
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.first

@Composable
fun ProductScreen(
    viewModel: ProductsViewModel = hiltViewModel(),
    @SuppressLint("ContextCastToActivity") mainViewModel: MainViewModel = hiltViewModel(LocalContext.current as ComponentActivity),
    onProductClick: (Int) -> Unit
) {
    val products = viewModel.products.collectAsLazyPagingItems()
    val categories by viewModel.categories.collectAsState()
    val selectedCategory by viewModel.selectedCategory.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()
    val currentSort by viewModel.sortOrder.collectAsState()
    val gridState = rememberLazyGridState()


    var lastCategory by remember { mutableStateOf(selectedCategory) }
    var lastQuery by remember { mutableStateOf(searchQuery) }
    var lastSort by remember { mutableStateOf(currentSort) }

    LaunchedEffect(selectedCategory, searchQuery, currentSort) {
        if (selectedCategory != lastCategory || searchQuery != lastQuery || currentSort != lastSort) {

            lastCategory = selectedCategory
            lastQuery = searchQuery
            lastSort = currentSort

            snapshotFlow { products.loadState.refresh }
                .filter { it is LoadState.NotLoading }
                .first()

            gridState.scrollToItem(0)
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        AppTopBar(
            titleText = "Shop",
            currentSort = currentSort,
            onSortSelected = { viewModel.onSortOrderChanged(it) }
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ) {

            //  Spacer(modifier = Modifier.height(8.dp))

            SearchBar(onSearch = { viewModel.onSearchQueryChanged(it) })

            Spacer(modifier = Modifier.height(16.dp))

            CategoryChips(
                categories = categories,
                selectedCategory = selectedCategory,
                onCategoryClick = { viewModel.onCategorySelected(it) }
            )

            Spacer(modifier = Modifier.height(16.dp))

            ProductGrid(
                products = products,
                gridState = gridState,
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
}