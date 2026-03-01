package com.grizzlyfungames.ecommersappcompose.ui.cart

import android.annotation.SuppressLint
import androidx.activity.ComponentActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.grizzlyfungames.ecommersappcompose.ui.MainViewModel
import com.grizzlyfungames.ecommersappcompose.ui.cart.components.CartItem
import com.grizzlyfungames.ecommersappcompose.ui.products.components.EmptyStateItem
import com.grizzlyfungames.ecommersappcompose.ui.topbar.AppTopBar

@Composable
fun CartScreen(
    viewModel: CartViewModel = hiltViewModel(),
    @SuppressLint("ContextCastToActivity") mainViewModel: MainViewModel = hiltViewModel(LocalContext.current as ComponentActivity),

    ) {
    val cartItems by viewModel.cartItems.collectAsState()
    val totalPrice by viewModel.totalPrice.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        AppTopBar("Cart")

        if (cartItems.isEmpty()) {
            EmptyStateItem(message = "Your cart is empty")
        } else {
            LazyColumn(
                modifier = Modifier.weight(1f),
                contentPadding = PaddingValues(bottom = 16.dp)
            ) {
                items(
                    items = cartItems,
                    key = { item -> item.id }
                ) { item ->
                    CartItem(
                        item = item,
                        onIncrement = { viewModel.incrementQuantity(it) },
                        onDecrement = { viewModel.decrementQuantity(it) },
                        onRemove = {
                            viewModel.removeItem(it)
                            val message = "${item.title} removed from Cart"
                            mainViewModel.showMessage(message)
                        },
                        modifier = Modifier.animateItem()
                    )
                }
            }

            Surface(
                color = MaterialTheme.colorScheme.surfaceVariant,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "Total",
                            color = Color.Gray,
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Text(
                            text = "$${"%.2f".format(totalPrice)}",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Button(
                        onClick = { },
                        shape = RoundedCornerShape(12.dp),
                    ) {
                        Text(
                            text = "Checkout",
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }
        }
    }
}