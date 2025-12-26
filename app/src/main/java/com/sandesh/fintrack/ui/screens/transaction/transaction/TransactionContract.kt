package com.sandesh.fintrack.ui.screens.transaction.transaction

import androidx.compose.ui.graphics.Color
import com.sandesh.fintrack.ui.screens.transaction.addTransaction.BalanceUiState
import com.sandesh.fintrack.ui.screens.transaction.transactionSuccess.TransactionUiModel

// ----------------------------
// DATA MODEL
// ----------------------------


data class TransactionItem(
    val title: String,
    val category: String,
    val time: Long,
    val amount: String,
    val isExpense: Boolean,
    val iconBg: Color
)


// ----------------------------
// STATE
// ----------------------------


data class TransactionsState(
    val balance: BalanceUiState = BalanceUiState(),
    val todayList: List<TransactionUiModel> = emptyList(),
    val yesterdayList: List<TransactionUiModel> = emptyList(),
    val selectedFilter: TransactionFilter = TransactionFilter.ALL,
)


enum class TransactionFilter { ALL, INCOME, EXPENSE , DATE}


// ----------------------------
// EVENT
// ----------------------------


sealed class TransactionsEvent {
    data class OnFilterChange(val filter: TransactionFilter) : TransactionsEvent()
    data object LoadTransactions : TransactionsEvent()
}


// ----------------------------
// EFFECT
// ----------------------------


sealed class TransactionsEffect {
    data object NavigateToAddTransaction : TransactionsEffect()
}