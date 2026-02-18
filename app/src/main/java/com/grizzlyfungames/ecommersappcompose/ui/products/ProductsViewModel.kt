package com.grizzlyfungames.ecommersappcompose.ui.products


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.grizzlyfungames.ecommersappcompose.data.local.entity.CategoryEntity
import com.grizzlyfungames.ecommersappcompose.domain.repository.CategoryRepository
import com.grizzlyfungames.ecommersappcompose.domain.repository.ProductRepository
import com.grizzlyfungames.ecommersappcompose.domain.util.SortOrder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
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
    private val _sortOrder = MutableStateFlow(SortOrder.DEFAULT)
    val selectedCategory: StateFlow<String?> = _selectedCategory.asStateFlow()
    val searchQuery: StateFlow<String?> = _searchQuery.asStateFlow()
    val sortOrder = _sortOrder.asStateFlow()

    val categories: StateFlow<List<CategoryEntity>> = categoryRepository.getCategories()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    @OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
    val products = combine(
        _searchQuery.debounce(500),
        _selectedCategory,
        _sortOrder
    ) { query, category, sort ->
        Triple(query, category, sort)
    }.flatMapLatest { (query, category, sort) ->
        productRepository.getProducts(query, category, sort)
    }.cachedIn(viewModelScope)

    fun onSearchQueryChanged(query: String?) {
        _searchQuery.value = if (query.isNullOrBlank()) null else query

        // if (query != null) _selectedCategory.value = null
    }

    fun onCategorySelected(categorySlug: String?) {
        _selectedCategory.value = categorySlug

        //if (categorySlug != null) _searchQuery.value = null
    }

    fun onSortOrderChanged(newOrder: SortOrder) {
        _sortOrder.value = newOrder
    }
}