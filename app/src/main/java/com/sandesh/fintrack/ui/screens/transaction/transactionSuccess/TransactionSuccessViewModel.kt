package com.sandesh.fintrack.ui.screens.transaction.transactionSuccess

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sandesh.fintrack.domain.TransactionRepository
import com.sandesh.fintrack.ui.screens.transaction.transaction.TransactionItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch


class TransactionSuccessViewModel(
    private val transactionId: Long,
    private val repository: TransactionRepository
) : ViewModel() {

    private val _transactionUi =
        MutableStateFlow<TransactionUiModel?>(null)
    val transactionUi = _transactionUi.asStateFlow()


    val transaction =
        _transactionUi
            .map { ui ->
                ui?.let {
                    TransactionItem(
                        title = it.source,
                        category = it.category, //
                        time = it.date,
                        amount = "₹${it.amount}",
                        isExpense = it.source == "Expense",
                        iconBg = Color(0xFF4CAF50)
                    )
                }
            }
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5_000),
                null
            )

    init {
        loadTransaction()
    }

    private fun loadTransaction() {
        viewModelScope.launch {
            val transaction = repository.getTransactionById(transactionId)

            _transactionUi.value =
                TransactionUiModel(
                    amount = transaction.amount,
                    category = transaction.category,
                    source = if (transaction.isIncome) "Income" else "Expense",
                    date = transaction.date,

                )
        }
    }
}



enum class CharCategory {
    FOOD, SHOPPING, RENT, SALARY, OTHER;

    companion object {
        fun from(value: String): CharCategory =
            values().firstOrNull {
                it.name.equals(value, ignoreCase = true)
            } ?: OTHER
    }
}





