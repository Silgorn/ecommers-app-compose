package com.grizzlyfungames.ecommersappcompose.ui.products.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.itemKey
import com.grizzlyfungames.ecommersappcompose.data.local.entity.ProductEntity
import com.grizzlyfungames.ecommersappcompose.ui.products.components.shimmer.ProductShimmerItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductGrid(
    products: LazyPagingItems<ProductEntity>,
    gridState: LazyGridState,
    onProductClick: (Int) -> Unit,
    onFavoriteToggle: (ProductEntity) -> Unit
) {
    val pullToRefreshState = rememberPullToRefreshState()

    val isLoading = products.loadState.refresh is LoadState.Loading

    PullToRefreshBox(
        state = pullToRefreshState,
        isRefreshing = isLoading && products.itemCount > 0 && pullToRefreshState.distanceFraction > 0f,
        onRefresh = { products.refresh() },
        modifier = Modifier.fillMaxSize()
    ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            state = gridState,
            contentPadding = PaddingValues(),
        ) {
            if (isLoading && products.itemCount == 0) {
                items(10) { ProductShimmerItem() }
            } else {
                items(
                    count = products.itemCount,
                    key = products.itemKey { it.id }
                ) { index ->
                    products[index]?.let { product ->
                        ProductItem(
                            product = product,
                            onClick = { onProductClick(product.id) },
                            onFavoriteClick = { onFavoriteToggle(product) }
                        )
                    }
                }
            }

            val loadState = products.loadState
            when {
                loadState.refresh is LoadState.NotLoading && products.itemCount == 0 -> {
                    item(span = { GridItemSpan(maxLineSpan) }) {
                        EmptyStateItem(message = "Products not found")
                    }
                }

                loadState.refresh is LoadState.Error -> {
                    item(span = { GridItemSpan(maxLineSpan) }) {
                        ErrorItem(message = "Loading error", onRetry = { products.retry() })
                    }
                }

                loadState.append is LoadState.Loading -> {
                    item(span = { GridItemSpan(maxLineSpan) }) {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .padding(16.dp)
                                .wrapContentWidth(Alignment.CenterHorizontally)
                        )
                    }
                }
            }
        }
    }
}