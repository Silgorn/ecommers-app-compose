package com.grizzlyfungames.ecommersappcompose.ui.products

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
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.grizzlyfungames.ecommersappcompose.ui.products.components.CategoryChips
import com.grizzlyfungames.ecommersappcompose.ui.products.components.ProductGrid
import com.grizzlyfungames.ecommersappcompose.ui.products.components.SearchBar

@Composable
fun ProductScreen(
    viewModel: ProductsViewModel,
    onProductClick: (Int) -> Unit
) {
    val products = viewModel.products.collectAsLazyPagingItems()
    val categories by viewModel.categories.collectAsState()
    val selectedCategory by viewModel.selectedCategory.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()
    val gridState = rememberLazyGridState()

    LaunchedEffect(selectedCategory, searchQuery) {
        gridState.scrollToItem(0)
    }
    LaunchedEffect(products.loadState.refresh) {
        if (products.loadState.refresh is LoadState.Loading) {
            gridState.scrollToItem(0)
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        SearchBar(onSearch = {
            viewModel.onSearchQueryChanged(it)
        })
        Spacer(modifier = Modifier.height(16.dp))
        CategoryChips(
            categories = categories,
            selectedCategory = selectedCategory,
            onCategoryClick = { viewModel.onCategorySelected(it) })
        ProductGrid(products = products, gridState = gridState) {}
    }

}