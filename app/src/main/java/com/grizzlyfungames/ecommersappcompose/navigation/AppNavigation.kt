package com.grizzlyfungames.ecommersappcompose.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.grizzlyfungames.ecommersappcompose.ui.details.ProductDetailScreen
import com.grizzlyfungames.ecommersappcompose.ui.products.ProductScreen

@Composable
fun AppNavigation(navController: NavHostController, paddingValues: PaddingValues) {


    NavHost(
        navController = navController,
        startDestination = "product_list",
        modifier = Modifier.padding(
            bottom = paddingValues.calculateBottomPadding() + 16.dp,
            top = paddingValues.calculateTopPadding()
        )
    ) {
        composable("product_list") {
            ProductScreen(
                onProductClick = { productId ->
                    navController.navigate("product_detail/$productId")
                }
            )
        }
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
        composable("favorites") {
            Box(Modifier.fillMaxSize()) {
                Text(
                    "Favorites", Modifier.align(
                        Alignment.Center
                    )
                )
            }
        }
        composable("cart") {
            Box(Modifier.fillMaxSize()) {
                Text(
                    "Cart",
                    Modifier.align(Alignment.Center)
                )
            }
        }
        composable("profile") {
            Box(Modifier.fillMaxSize()) {
                Text(
                    "Profile",
                    Modifier.align(Alignment.Center)
                )
            }
        }
    }
}