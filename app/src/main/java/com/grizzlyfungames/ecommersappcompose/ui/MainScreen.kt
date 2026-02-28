package com.grizzlyfungames.ecommersappcompose.ui

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.grizzlyfungames.ecommersappcompose.navigation.AppBottomBar
import com.grizzlyfungames.ecommersappcompose.navigation.AppNavigation

@Composable
fun MainScreen(
    viewModel: MainViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val favoritesCount by viewModel.favoritesCount.collectAsState()

    val bottomBarScreens = listOf("product_list", "favorites", "cart", "profile")
    val shouldShowBottomBar = currentRoute in bottomBarScreens

    Scaffold(
        bottomBar = {
            if (shouldShowBottomBar) {
                AppBottomBar(navController, favoritesCount)
            }
        },
    ) { paddingValues ->
        AppNavigation(navController, paddingValues)
    }
}