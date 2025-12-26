package com.sandesh.fintrack.ui.screens.transaction.transaction

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sandesh.fintrack.common.utils.isToday
import com.sandesh.fintrack.common.utils.isYesterday
import com.sandesh.fintrack.domain.TransactionModel
import com.sandesh.fintrack.domain.TransactionRepository
import com.sandesh.fintrack.mappper.toUi
import com.sandesh.fintrack.ui.screens.transaction.addTransaction.BalanceUiState
import com.sandesh.fintrack.ui.screens.transaction.transactionSuccess.TransactionUiModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
class TransactionsViewModel(
    private val repository: TransactionRepository
) : ViewModel() {

    private val _state = MutableStateFlow(TransactionsState())
    val state: StateFlow<TransactionsState> = _state

    private val _effect = Channel<TransactionsEffect>()
    val effect = _effect.receiveAsFlow()

    init {
        onEvent(TransactionsEvent.LoadTransactions)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun onEvent(event: TransactionsEvent) {
        when (event) {
            is TransactionsEvent.OnFilterChange -> {
                _state.update {
                    it.copy(selectedFilter = event.filter)
                }
            }
            TransactionsEvent.LoadTransactions -> {
                loadTransactions()
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun loadTransactions() {
        viewModelScope.launch {
            repository.observeTransactions().collect { list ->

                val balance = calculateBalance(list)
                val filtered = applyFilter(list, state.value.selectedFilter)
                val (today, yesterday) = splitByDate(filtered)

                _state.update {
                    it.copy(
                        balance = balance,
                        todayList = today,
                        yesterdayList = yesterday
                    )
                }
            }
        }
    }

}



fun applyFilter(
    list: List<TransactionModel>,
    filter: TransactionFilter
): List<TransactionModel> {
    return when (filter) {
        TransactionFilter.ALL -> list

        TransactionFilter.INCOME ->
            list.filter { it.isIncome }

        TransactionFilter.EXPENSE ->
            list.filter { !it.isIncome }

        TransactionFilter.DATE ->
            list.sortedByDescending { it.date }
    }
}



@RequiresApi(Build.VERSION_CODES.O)
private fun splitByDate(
    list: List<TransactionModel>
): Pair<List<TransactionUiModel>, List<TransactionUiModel>> {

    val today = mutableListOf<TransactionUiModel>()
    val yesterday = mutableListOf<TransactionUiModel>()

    list.forEach {
        when {
            isToday(it.date) -> today.add(it.toUi())
            isYesterday(it.date) -> yesterday.add(it.toUi())
        }
    }
    return today to yesterday
}

private fun calculateBalance(list: List<TransactionModel>): BalanceUiState {

    val income = list
        .filter { it.isIncome }
        .sumOf { it.amount }

    val expense = list
        .filter { !it.isIncome }
        .sumOf { it.amount }

    return BalanceUiState(
        total = income - expense,
        income = income,
        expense = expense
    )
}


