package com.sandesh.fintrack.ui.screens.dashboard.recentTransaction

import com.sandesh.fintrack.domain.TransactionModel
import com.sandesh.fintrack.ui.screens.transaction.addTransaction.BalanceUiState

data class TransactionsUiState(
    val transactions: List<TransactionModel> = emptyList(),
    val balance: BalanceUiState = BalanceUiState()
)
