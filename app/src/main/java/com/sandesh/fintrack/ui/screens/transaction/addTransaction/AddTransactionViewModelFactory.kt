package com.sandesh.fintrack.ui.screens.transaction.addTransaction

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class AddTransactionViewModelFactory : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddTransactionViewModel::class.java)) {
            return AddTransactionViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel")
    }
}
