package com.grizzlyfungames.ecommersappcompose.ui.products.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.grizzlyfungames.ecommersappcompose.data.local.entity.CategoryEntity

@Composable
fun CategoryChips(
    categories: List<CategoryEntity>,
    selectedCategory: String?,
    onCategoryClick: (String?) -> Unit
) {
    LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        item {
            FilterChip(
                selected = selectedCategory == null,
                onClick = { onCategoryClick(null) },
                label = { Text("Все") },
                leadingIcon = if (selectedCategory == null) {
                    {
                        Icon(
                            Icons.Default.Done,
                            contentDescription = null,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                } else null
            )
        }
        items(categories) { category ->
            FilterChip(
                selected = category.slug == selectedCategory,
                onClick = { onCategoryClick(category.slug) },
                label = { Text(category.name) },
                leadingIcon = if (category.slug == selectedCategory) {
                    {
                        Icon(
                            Icons.Default.Done,
                            contentDescription = null,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                } else null
            )
        }
    }
}