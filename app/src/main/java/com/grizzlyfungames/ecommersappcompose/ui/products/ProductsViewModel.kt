package com.grizzlyfungames.ecommersappcompose.ui.products


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.grizzlyfungames.ecommersappcompose.data.local.entity.CategoryEntity
import com.grizzlyfungames.ecommersappcompose.domain.repository.CategoryRepository
import com.grizzlyfungames.ecommersappcompose.domain.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class ProductsViewModel @Inject constructor(
    private val productRepository: ProductRepository,
    private val categoryRepository: CategoryRepository,
) : ViewModel() {

    private val _searchQuery = MutableStateFlow<String?>(null)
    private val _selectedCategory = MutableStateFlow<String?>(null)

    val categories: StateFlow<List<CategoryEntity>> = categoryRepository.getCategories()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    @OptIn(ExperimentalCoroutinesApi::class)
    val products = combine(_searchQuery, _selectedCategory) { query, category ->
        Pair(query, category)
    }.flatMapLatest { (query, category) ->
        productRepository.getProducts(query, category)
    }.cachedIn(viewModelScope)

    fun onSearchQueryChanged(query: String?) {
        _searchQuery.value = if (query.isNullOrBlank()) null else query

        if (query != null) _selectedCategory.value = null
    }

    fun onCategorySelected(categorySlug: String?) {
        _selectedCategory.value = categorySlug

        if (categorySlug != null) _searchQuery.value = null
    }
}