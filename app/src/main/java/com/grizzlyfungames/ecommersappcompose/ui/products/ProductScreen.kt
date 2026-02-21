package com.grizzlyfungames.ecommersappcompose.ui.products

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.grizzlyfungames.ecommersappcompose.ui.products.components.CategoryChips
import com.grizzlyfungames.ecommersappcompose.ui.products.components.ProductGrid
import com.grizzlyfungames.ecommersappcompose.ui.products.components.SearchBar
import com.grizzlyfungames.ecommersappcompose.ui.topbar.AppTopBar
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.yield

@Composable
fun ProductScreen(
    viewModel: ProductsViewModel = hiltViewModel(),
    onProductClick: (Int) -> Unit
) {
    val products = viewModel.products.collectAsLazyPagingItems()
    val categories by viewModel.categories.collectAsState()
    val selectedCategory by viewModel.selectedCategory.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()
    val currentSort by viewModel.sortOrder.collectAsState()
    val gridState = rememberLazyGridState()

    LaunchedEffect(selectedCategory, searchQuery, currentSort) {
        snapshotFlow { products.loadState.refresh }
            .filter { it is LoadState.NotLoading }
            .collect {
                yield()
                gridState.animateScrollToItem(0)
            }
    }
    Scaffold(
        topBar = { AppTopBar(viewModel, currentSort) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
        ) {
            Spacer(modifier = Modifier.height(8.dp))

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
                onProductClick = onProductClick
            )
        }
    }
}