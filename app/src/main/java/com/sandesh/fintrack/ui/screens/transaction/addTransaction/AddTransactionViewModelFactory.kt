package com.sandesh.fintrack.ui.screens.transaction.addTransaction

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sandesh.fintrack.domain.TransactionRepository

class AddTransactionViewModelFactory(
    private val repository: TransactionRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddTransactionViewModel::class.java)) {
            return AddTransactionViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel")
    }
}
