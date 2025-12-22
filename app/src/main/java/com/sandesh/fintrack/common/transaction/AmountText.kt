package com.sandesh.fintrack.common.transaction

import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import com.sandesh.fintrack.ui.theme.TextPrimary

@Composable
fun AmountText(amount: String) {
    Text(
        text = "₹ $amount",
        style = MaterialTheme.typography.displaySmall,
        color = TextPrimary
    )
}
