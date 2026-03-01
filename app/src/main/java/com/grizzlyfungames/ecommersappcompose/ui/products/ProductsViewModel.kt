package com.grizzlyfungames.ecommersappcompose.ui.products


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.grizzlyfungames.ecommersappcompose.data.local.entity.CategoryEntity
import com.grizzlyfungames.ecommersappcompose.data.local.entity.ProductEntity
import com.grizzlyfungames.ecommersappcompose.domain.repository.CategoryRepository
import com.grizzlyfungames.ecommersappcompose.domain.repository.FavoritesRepository
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
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductsViewModel @Inject constructor(
    private val productRepository: ProductRepository,
    private val categoryRepository: CategoryRepository,
    private val favoritesRepository: FavoritesRepository,
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
        _sortOrder,
        favoritesRepository.getFavoriteIds()
    ) { query: String?, category: String?, sort: SortOrder, favIds: List<Int> ->
        val favSet = favIds.toSet()

        productRepository.getProducts(query, category, sort)
            .map { pagingData: PagingData<ProductEntity> ->
                pagingData.map { product: ProductEntity ->
                    product.copy(isFavorite = favSet.contains(product.id))
                }
            }
    }.flatMapLatest { it }
        .cachedIn(viewModelScope)

    fun onSearchQueryChanged(query: String?) {
        _searchQuery.value = if (query.isNullOrBlank()) null else query
    }

    fun onCategorySelected(categorySlug: String?) {
        _selectedCategory.value = categorySlug
    }

    fun onSortOrderChanged(newOrder: SortOrder) {
        _sortOrder.value = newOrder
    }

    fun onFavoriteToggle(product: ProductEntity) {
        viewModelScope.launch {
            favoritesRepository.toggleFavorite(product, product.isFavorite)
        }
    }
}