package com.grizzlyfungames.ecommersappcompose.ui

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.grizzlyfungames.ecommersappcompose.navigation.AppBottomBar
import com.grizzlyfungames.ecommersappcompose.navigation.AppNavigation

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val bottomBarScreens = listOf("product_list", "favorites", "cart", "profile")
    val shouldShowBottomBar = currentRoute in bottomBarScreens

    Scaffold(
        bottomBar = {
            if (shouldShowBottomBar) {
                AppBottomBar(navController)
            }
        },
    ) { paddingValues ->
        AppNavigation(navController, paddingValues)
    }
}