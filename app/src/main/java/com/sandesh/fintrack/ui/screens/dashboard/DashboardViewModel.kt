package com.sandesh.fintrack.ui.screens.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sandesh.fintrack.domain.TransactionRepositoryImpl
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn

class DashboardViewModel(
    repository: TransactionRepositoryImpl
) : ViewModel() {

    val income = repository.totalIncome()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0.0)

    val expense = repository.totalExpense()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0.0)

    val totalBalance = repository.totalBalance()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0.0)
}
