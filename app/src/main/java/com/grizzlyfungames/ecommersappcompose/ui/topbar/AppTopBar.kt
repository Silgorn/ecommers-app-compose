package com.grizzlyfungames.ecommersappcompose.ui.topbar

import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import com.grizzlyfungames.ecommersappcompose.domain.util.SortOrder
import com.grizzlyfungames.ecommersappcompose.ui.products.ProductsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar(
    viewModel: ProductsViewModel,
    currentSort: SortOrder
) {
    CenterAlignedTopAppBar(
        title = { Text("Shop", style = MaterialTheme.typography.titleLarge) },
        actions = {
            SortDropDownMenu(
                currentSort = currentSort,
                onSortSelected = { viewModel.onSortOrderChanged(it) }
            )
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    )
}