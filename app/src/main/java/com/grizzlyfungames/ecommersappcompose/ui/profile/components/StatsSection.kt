package com.grizzlyfungames.ecommersappcompose.ui.profile.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.grizzlyfungames.ecommersappcompose.domain.model.UserProfile

@Composable
fun StatsSection(user: UserProfile) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        StatCard("Orders", user.ordersCount.toString(), Modifier.weight(1f))
        StatCard("Bonuses", "${user.bonusPoints} $", Modifier.weight(1f))
    }
}