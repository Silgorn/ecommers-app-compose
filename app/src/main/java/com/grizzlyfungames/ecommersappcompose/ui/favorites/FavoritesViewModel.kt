package com.grizzlyfungames.ecommersappcompose.ui.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.grizzlyfungames.ecommersappcompose.data.local.entity.ProductEntity
import com.grizzlyfungames.ecommersappcompose.domain.repository.FavoritesRepository
import com.grizzlyfungames.ecommersappcompose.domain.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val productRepository: ProductRepository,
    private val favoriteRepository: FavoritesRepository
) : ViewModel() {
    val favoriteProducts = productRepository.getFavoriteProductsFlow()
        .map { pagingData: PagingData<ProductEntity> ->
            pagingData.map { product: ProductEntity ->
                product.copy(isFavorite = true)
            }
        }
        .cachedIn(viewModelScope)

    fun onFavoriteToggle(product: ProductEntity) {
        viewModelScope.launch {
            favoriteRepository.toggleFavorite(product, true)
        }
    }
}