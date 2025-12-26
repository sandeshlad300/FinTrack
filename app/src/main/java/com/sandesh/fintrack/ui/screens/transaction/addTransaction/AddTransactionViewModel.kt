package com.sandesh.fintrack.ui.screens.transaction.addTransaction

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sandesh.fintrack.domain.TransactionModel
import com.sandesh.fintrack.domain.TransactionRepository

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AddTransactionViewModel(
    private val repository: TransactionRepository
) : ViewModel() {

    private val _state = MutableStateFlow(AddTransactionState())
    val state: StateFlow<AddTransactionState> = _state

    private val _effect = Channel<AddTransactionEffect>(Channel.BUFFERED)
    val effect = _effect.receiveAsFlow()

    @RequiresApi(Build.VERSION_CODES.O)
    fun onIntent(intent: AddTransactionIntent) {
        when (intent) {

            is AddTransactionIntent.BackClicked -> {
                sendEffect(AddTransactionEffect.NavigateBack)
            }

            AddTransactionIntent.SaveClicked -> {
                if (!state.value.isFormValid) return

                viewModelScope.launch {
                    _state.update { it.copy(isLoading = true) }

                    delay(2000)

                    val transactionId = repository.insert(
                        TransactionModel(
                            id = 0L,
                            amount = state.value.amount.toDouble(),
                            category = state.value.category,
                            note = state.value.note,
                            isIncome = state.value.isIncome,
                            date = state.value.dateMillis
                        )
                    )

                    _state.update { it.copy(isLoading = false) }

                    sendEffect(
                        AddTransactionEffect.NavigateToSuccess(transactionId)
                    )
                }
            }



            is AddTransactionIntent.TransactionTypeChanged -> {
                val categories = if (intent.isIncome) {
                    CategoryData.incomeCategories
                } else {
                    CategoryData.expenseCategories
                }

                _state.update {
                    it.copy(
                        isIncome = intent.isIncome,
                        availableCategories = categories,
                        category = "" // reset selection on tab switch
                    )
                }
            }



            AddTransactionIntent.DismissCategorySheet -> {
                _state.update {
                    it.copy(showCategorySheet = false)
                }
            }


            is AddTransactionIntent.AmountChanged -> {
                _state.update {
                    it.copy(
                        amount = intent.amount,
                        showAmountError = false // reset error on edit
                    )
                }
            }

            AddTransactionIntent.CategoryClicked -> {
                _state.update {
                    it.copy(showCategorySheet = true)
                }
            }


            is AddTransactionIntent.DateClicked -> {
                sendEffect(AddTransactionEffect.OpenDatePicker)
            }

            is AddTransactionIntent.DateSelected -> {
                _state.update {
                    it.copy(date = intent.date)
                }
            }

            is AddTransactionIntent.NoteChanged -> {
                _state.update {
                    it.copy(note = intent.note)
                }
            }

            is AddTransactionIntent.CategorySelected -> {
                _state.update {
                    it.copy(
                        category = intent.category,
                        showCategorySheet = false
                    )
                }
            }

        }
    }

    private fun sendEffect(effect: AddTransactionEffect) {
        viewModelScope.launch {
            _effect.send(effect)
        }
    }

}



data class BalanceUiState(
    val total: Double = 0.0,
    val income: Double = 0.0,
    val expense: Double = 0.0
)






