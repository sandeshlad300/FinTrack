package com.sandesh.fintrack.ui.screens.transaction.transaction

import androidx.compose.ui.graphics.Color

// ----------------------------
// DATA MODEL
// ----------------------------


data class TransactionItem(
    val title: String,
    val category: String,
    val time: String,
    val amount: String,
    val isExpense: Boolean,
    val iconBg: Color
)


// ----------------------------
// STATE
// ----------------------------


data class TransactionsState(
    val selectedFilter: TransactionFilter = TransactionFilter.ALL,
    val todayList: List<TransactionItem> = emptyList(),
    val yesterdayList: List<TransactionItem> = emptyList(),
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