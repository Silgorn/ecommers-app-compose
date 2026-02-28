package com.grizzlyfungames.ecommersappcompose.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun AppBottomBar(navController: NavController, favoritesCount: Int) {
    val items = listOf(
        BottomNavItem.Home,
        BottomNavItem.Favorites,
        BottomNavItem.Cart,
        BottomNavItem.Profile
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    NavigationBar(
        containerColor = MaterialTheme.colorScheme.surface,
    ) {
        items.forEach { item ->
            val isSelected = currentRoute == item.route

            NavigationBarItem(
                selected = isSelected,
                onClick = {
                    if (currentRoute != item.route) {
                        navController.navigate(item.route) {
                            popUpTo(navController.graph.startDestinationId) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                },
                label = { Text(item.label) },
                icon = {
                    if (item is BottomNavItem.Favorites) {
                        BadgedBox(
                            badge = {
                                if (favoritesCount > 0 && !isSelected) {
                                    Badge(
                                        containerColor = MaterialTheme.colorScheme.error,
                                        contentColor = Color.White
                                    ) {
                                        Text(if (favoritesCount > 99) "99+" else favoritesCount.toString())
                                    }
                                }
                            }
                        ) {
                            Icon(
                                imageVector = if (isSelected) Icons.Filled.Favorite else item.icon,
                                contentDescription = item.label
                            )
                        }
                    } else {
                        Icon(item.icon, contentDescription = item.label)
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = MaterialTheme.colorScheme.primary,
                    selectedIconColor = MaterialTheme.colorScheme.onPrimary,
                    selectedTextColor = MaterialTheme.colorScheme.primary
                )
            )
        }
    }
}

sealed class BottomNavItem(
    val route: String,
    val icon: ImageVector,
    val label: String
) {
    object Home : BottomNavItem("product_list", Icons.Default.Home, "Home")
    object Favorites : BottomNavItem("favorites", Icons.Default.FavoriteBorder, "Favorites")
    object Cart : BottomNavItem("cart", Icons.Default.ShoppingCart, "Cart")
    object Profile : BottomNavItem("profile", Icons.Default.Person, "Profile")
}