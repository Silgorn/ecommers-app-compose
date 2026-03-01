package com.grizzlyfungames.ecommersappcompose.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.grizzlyfungames.ecommersappcompose.domain.repository.CartRepository
import com.grizzlyfungames.ecommersappcompose.domain.repository.FavoritesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val favoriteRepository: FavoritesRepository,
    private val cartRepository: CartRepository
) : ViewModel() {

    val favoritesCount: StateFlow<Int> = favoriteRepository.getFavoriteIds()
        .map { it.size }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = 0
        )

    val cartItemsCount: StateFlow<Int> = cartRepository.getCartItemIds()
        .map { it.size }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = 0
        )
}