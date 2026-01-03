package com.sandesh.fintrack.ui.screens.transaction.transactionSuccess

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sandesh.fintrack.common.GradientButton
import com.sandesh.fintrack.common.transaction.SuccessIcon
import com.sandesh.fintrack.common.transaction.TransactionSummaryCard



@Composable
fun TransactionSuccessScreen(
    viewModel: TransactionSuccessViewModel,
    onAddAnotherTransaction: () -> Unit,
    onViewAllTransactions: () -> Unit
) {
    val transactionUi by viewModel.transactionUi.collectAsState()
    if (transactionUi == null) return

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(Color(0xFF140B2D), Color(0xFF090417))
                )
            )
            .windowInsetsPadding(WindowInsets.safeDrawing)
            .padding(horizontal = 24.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(Modifier.height(60.dp))
            SuccessIcon()
            Spacer(Modifier.height(24.dp))

            Text(
                text = "Transaction Added!",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

            TransactionSummaryCard(
                transaction = transactionUi!!,
                modifier = Modifier.padding(top = 32.dp)
            )

            Spacer(Modifier.weight(1f))

            GradientButton(
                text = "Add Another Transaction",
                onClick = onAddAnotherTransaction
            )

            Spacer(Modifier.height(16.dp))

            TextButton(onClick = onViewAllTransactions) {
                Text("View All Transactions", color = Color.White)
            }
        }
    }
}
