package com.sandesh.fintrack.ui.screens.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun RecentTransactionSection() {

    val list = listOf(
        Transaction("Spotify Subscription", "June 15, 2024", "-$9.99", true),
        Transaction("Starbucks Coffee", "June 14, 2024", "-$5.75", true),
        Transaction("Amazon Purchase", "June 12, 2024", "-$42.50", true),
        Transaction("Salary Deposit", "June 10, 2024", "+$2,500.00", false)
    )

    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            "Recent Transactions",
            color = Color.White,
            fontWeight = FontWeight.SemiBold,
            fontSize = 18.sp
        )

        Text("View All", color = Color(0xFF56D9C7), fontSize = 14.sp)
    }

    Spacer(Modifier.height(12.dp))

    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        list.forEach {
            TransactionCard(it)
        }
    }
}

data class Transaction(
    val title: String,
    val date: String,
    val amount: String,
    val isExpense: Boolean
)

@Composable
fun TransactionCard(item: Transaction) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(Color(0xFF191A37))
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(Icons.Default.ShoppingCart, contentDescription = null, tint = Color.White)

        Spacer(Modifier.width(16.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(item.title, color = Color.White, fontSize = 16.sp)
            Text(item.date, color = Color.Gray, fontSize = 12.sp)
        }

        Text(
            item.amount,
            color = if (item.isExpense) Color(0xFFFF5B5B) else Color(0xFF4BE28A),
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )
    }
}