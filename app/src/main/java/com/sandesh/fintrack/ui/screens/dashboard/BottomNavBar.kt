package com.sandesh.fintrack.ui.screens.dashboard

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.sandesh.fintrack.R

@Composable
fun BottomNavBar(
    selected: Int = 0,
    onChange: (Int) -> Unit
) {
    NavigationBar(
        containerColor = Color(0xFF141334)
    ) {
        // Replace these with your drawable icons
        val items = listOf(
            R.drawable.dashboard,
            R.drawable.transaction,
            R.drawable.analytics,
            R.drawable.settings
        )

        val labels = listOf("Dashboard", "Transactions", "Analytics", "Settings")

        items.forEachIndexed { index, iconRes ->
            NavigationBarItem(
                selected = selected == index,
                onClick = { onChange(index) },
                icon = {
                    Icon(
                        painter = painterResource(id = iconRes),
                        contentDescription = labels[index],
                        modifier = Modifier.size(24.dp),
                        tint = if (selected == index) Color(0xFF9C27B0) else Color.Gray
                    )
                },
                label = {
                    Text(
                        labels[index],
                        color = if (selected == index) Color.White else Color.Gray
                    )
                }
            )
        }
    }
}
