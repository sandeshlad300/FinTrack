package com.sandesh.fintrack.ui.screens.dashboard.recentTransaction

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sandesh.fintrack.domain.TransactionModel
import com.sandesh.fintrack.ui.screens.transaction.addTransaction.formatAmount

@Composable
fun RecentTransactionItem(
    transaction: TransactionModel
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF1B1938), RoundedCornerShape(14.dp))
            .padding(14.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        Column {
            Text(
                text = transaction.category,
                color = Color.White,
                fontSize = 14.sp
            )

            Spacer(Modifier.height(4.dp))

            Text(
                text = transaction.date.toString(),
                color = Color.White.copy(0.6f),
                fontSize = 12.sp
            )
        }

        Text(
            text = formatAmount(transaction.amount.toString()),
            color = if (transaction.isIncome) Color(0xFF4CAF50) else Color(0xFFFF5252),
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}
