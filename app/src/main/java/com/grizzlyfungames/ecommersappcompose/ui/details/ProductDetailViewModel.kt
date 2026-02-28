package com.grizzlyfungames.ecommersappcompose.ui.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.grizzlyfungames.ecommersappcompose.data.local.AppDatabase
import com.grizzlyfungames.ecommersappcompose.data.local.entity.ProductEntity
import com.grizzlyfungames.ecommersappcompose.domain.repository.FavoritesRepository
import com.grizzlyfungames.ecommersappcompose.domain.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductDetailViewModel @Inject constructor(
    private val db: AppDatabase,
    private val productRepository: ProductRepository,
    private val favoriteRepository: FavoritesRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val productId: Int = checkNotNull(savedStateHandle["productId"])

    val productState = kotlinx.coroutines.flow.combine(
        productRepository.getProductByIdFlow(productId),
        favoriteRepository.isFavoriteFlow(productId)
    ) { product, isFavorite ->
        product?.copy(isFavorite = isFavorite)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = null
    )

    fun toggleFavorite(product: ProductEntity) {
        viewModelScope.launch {
            favoriteRepository.toggleFavorite(product, product.isFavorite)
        }
    }

}