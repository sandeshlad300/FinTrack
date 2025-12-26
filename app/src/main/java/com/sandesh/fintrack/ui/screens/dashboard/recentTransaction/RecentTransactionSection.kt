package com.sandesh.fintrack.ui.screens.dashboard.recentTransaction

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sandesh.fintrack.common.transaction.TransactionCard
import com.sandesh.fintrack.domain.TransactionModel
import com.sandesh.fintrack.ui.screens.transaction.transactionSuccess.TransactionUiModel

@Composable
fun RecentTransactionSection(
    transactions: List<TransactionUiModel>
) {
    Column {
        Text(
            text = "Recent Transactions",
            color = Color.White,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(Modifier.height(12.dp))

        transactions.forEach {
            TransactionCard(item = it)
            Spacer(Modifier.height(10.dp))
        }
    }
}
