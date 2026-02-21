package com.grizzlyfungames.ecommersappcompose.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.grizzlyfungames.ecommersappcompose.ui.details.ProductDetailScreen
import com.grizzlyfungames.ecommersappcompose.ui.products.ProductScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "product_list" // Начальный экран
    ) {
        // Экран списка
        composable("product_list") {
            ProductScreen(
                onProductClick = { productId ->
                    // Переходим на экран деталей, передавая ID
                    navController.navigate("product_detail/$productId")
                }
            )
        }
        // Экран деталей с аргументом
        composable(
            route = "product_detail/{productId}",
            arguments = listOf(
                navArgument("productId") { type = NavType.IntType }
            )
        ) {
            ProductDetailScreen(
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
    }
}