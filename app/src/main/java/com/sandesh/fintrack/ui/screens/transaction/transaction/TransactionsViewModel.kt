package com.sandesh.fintrack.ui.screens.transaction.transaction

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update

class TransactionsViewModel : ViewModel() {


    private val _state = MutableStateFlow(TransactionsState())
    val state: StateFlow<TransactionsState> = _state


    private val _effect = Channel<TransactionsEffect>()
    val effect = _effect.receiveAsFlow()


    init {
        load()
    }


    fun onEvent(event: TransactionsEvent) {
        when (event) {
            is TransactionsEvent.OnFilterChange -> {
                _state.update { it.copy(selectedFilter = event.filter) }
            }
            TransactionsEvent.LoadTransactions -> load()
        }
    }


    private fun load() {
        val today = listOf(
            TransactionItem("Starbucks Coffee", "Food & Drink", "10:30 AM", "-$5.75", true, Color(0xFFEB8F33)),
            TransactionItem("Apple Store", "Electronics", "2:15 PM", "-$1,299.00", true, Color(0xFF8E44AD)),
            TransactionItem("Freelance Project", "Income", "4:00 PM", "+$850.00", false, Color(0xFF2ECC71))
        )


        val yesterday = listOf(
            TransactionItem("Metro Pass", "Transport", "8:45 AM", "-$35.00", true, Color(0xFF2980B9)),
            TransactionItem("Netflix Subscription", "Entertainment", "9:00 AM", "-$15.99", true, Color(0xFFE84393))
        )


        _state.update { it.copy(todayList = today, yesterdayList = yesterday) }
    }
}