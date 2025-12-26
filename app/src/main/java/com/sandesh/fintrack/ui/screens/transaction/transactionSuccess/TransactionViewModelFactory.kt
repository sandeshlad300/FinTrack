package com.sandesh.fintrack.ui.screens.transaction.transactionSuccess

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sandesh.fintrack.domain.TransactionRepository

class TransactionSuccessViewModelFactory(
    private val transactionId: Long,
    private val repository: TransactionRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TransactionSuccessViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return TransactionSuccessViewModel(
                transactionId = transactionId,
                repository = repository
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
