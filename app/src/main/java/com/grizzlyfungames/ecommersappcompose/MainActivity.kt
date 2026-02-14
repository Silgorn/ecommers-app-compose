package com.grizzlyfungames.ecommersappcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.hilt.navigation.compose.hiltViewModel
import com.grizzlyfungames.ecommersappcompose.ui.products.ProductScreen
import com.grizzlyfungames.ecommersappcompose.ui.products.ProductsViewModel
import com.grizzlyfungames.ecommersappcompose.ui.theme.ECommersAppComposeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ECommersAppComposeTheme {
                val viewModel: ProductsViewModel = hiltViewModel()
                ProductScreen(
                    viewModel = viewModel,
                    onProductClick = { id ->
                        println("Кликнули на героя с id: $id")
                    }
                )
            }
        }
    }
}

