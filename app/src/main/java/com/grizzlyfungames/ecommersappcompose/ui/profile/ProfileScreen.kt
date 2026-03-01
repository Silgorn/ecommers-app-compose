package com.grizzlyfungames.ecommersappcompose.ui.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.grizzlyfungames.ecommersappcompose.domain.model.UserProfile
import com.grizzlyfungames.ecommersappcompose.ui.profile.components.ProfileHeader
import com.grizzlyfungames.ecommersappcompose.ui.profile.components.StatsSection

@Composable
fun ProfileScreen() {
    val user = UserProfile(
        name = "Example User",
        email = "user.dev@example.com",
        avatarUrl = "https://example.com/avatar.jpg",
        ordersCount = 12,
        bonusPoints = 450
    )

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
    ) {
        item {
            ProfileHeader(user)
        }

        item {
            StatsSection(user)
        }

        item {
            Text(
                text = "Personal data",
                modifier = Modifier.padding(16.dp),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
        }

        val menuItems = listOf(
            "My orders" to Icons.Default.List,
            "Delivery addresses" to Icons.Default.LocationOn,
            "Payment methods" to Icons.Default.ShoppingCart,
            "Notification settings" to Icons.Default.Notifications,
            "Support" to Icons.Default.Info
        )

        items(menuItems) { item ->
            val (title, icon) = item
            ProfileMenuItem(title, icon)
        }

        item {
            TextButton(
                onClick = { },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                colors = ButtonDefaults.textButtonColors(contentColor = Color.Red)
            ) {
                Text("Log out")
            }
        }
    }
}

@Composable
fun ProfileMenuItem(title: String, icon: ImageVector) {
    Surface(
        onClick = { },
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = title,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.weight(1f)
            )
            Icon(
                imageVector = Icons.Default.KeyboardArrowRight,
                contentDescription = null,
                tint = Color.Gray
            )
        }
    }
}