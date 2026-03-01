package com.grizzlyfungames.ecommersappcompose.ui

import android.annotation.SuppressLint
import androidx.activity.ComponentActivity
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.grizzlyfungames.ecommersappcompose.navigation.AppBottomBar
import com.grizzlyfungames.ecommersappcompose.navigation.AppNavigation
import com.grizzlyfungames.ecommersappcompose.ui.utils.AppSnackbar

@Composable
fun MainScreen(
    @SuppressLint("ContextCastToActivity") viewModel: MainViewModel = hiltViewModel(LocalContext.current as ComponentActivity)
) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val favoritesCount by viewModel.favoritesCount.collectAsState()
    val cartItemsCount by viewModel.cartItemsCount.collectAsState()

    val bottomBarScreens = listOf("product_list", "favorites", "cart", "profile")
    val shouldShowBottomBar = currentRoute in bottomBarScreens

    val snackbarHostState = remember { SnackbarHostState() }
    val snackbarMessage by viewModel.snackbarEvent.collectAsState(initial = null)

    LaunchedEffect(snackbarMessage) {
        snackbarMessage?.let {
            snackbarHostState.showSnackbar(it)
            viewModel.onSnackbarShown()
        }
    }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.surface,
        snackbarHost = {
            AppSnackbar(snackbarHostState)
        },
        bottomBar = {
            if (shouldShowBottomBar) {
                AppBottomBar(navController, favoritesCount, cartItemsCount)
            }
        },
    ) { paddingValues ->
        AppNavigation(navController, paddingValues)
    }
}