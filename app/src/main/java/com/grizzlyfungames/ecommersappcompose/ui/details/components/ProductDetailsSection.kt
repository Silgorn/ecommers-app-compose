package com.grizzlyfungames.ecommersappcompose.ui.details.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ProductDetailsSection(
    title: String,
    rating: Double,
    price: Double,
    discountPercentage: Double,
    description: String,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier.fillMaxSize(),
        shape = RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp),
        color = Color.White
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Text(
                text = title,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )

            Spacer(Modifier.height(12.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                BadgeItem(icon = Icons.Default.Star, text = "$rating", subText = "117 reviews")
                Spacer(Modifier.width(8.dp))
                BadgeItem(
                    icon = Icons.Default.ThumbUp,
                    text = "94%",
                    color = MaterialTheme.colorScheme.surfaceVariant
                )
                Spacer(Modifier.width(8.dp))
                BadgeItem(
                    icon = Icons.Default.MailOutline,
                    text = "8",
                    color = MaterialTheme.colorScheme.surfaceVariant
                )
            }

            Spacer(Modifier.height(20.dp))

            ProductPriceCard(price = price, discountPercentage = discountPercentage)

            Spacer(Modifier.height(20.dp))

            Text(
                text = description,
                style = MaterialTheme.typography.bodyMedium,
                color = Color.DarkGray,
                lineHeight = 20.sp
            )
        }
    }
}

@Composable

fun BadgeItem(

    icon: ImageVector,

    text: String,

    subText: String? = null,

    color: Color = MaterialTheme.colorScheme.surfaceVariant

) {

    Surface(

        color = color,

        shape = RoundedCornerShape(8.dp)

    ) {

        Row(

            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),

            verticalAlignment = Alignment.CenterVertically

        ) {

            Icon(

                icon,

                contentDescription = null,

                modifier = Modifier.size(14.dp),

                tint = MaterialTheme.colorScheme.onPrimary

            )

            Spacer(Modifier.width(4.dp))

            Text(

                text = text,

                style = MaterialTheme.typography.labelLarge,

                fontWeight = FontWeight.Bold

            )

            if (subText != null) {

                Spacer(Modifier.width(4.dp))

                Text(subText, style = MaterialTheme.typography.labelSmall, color = Color.Gray)

            }

        }
    }
}