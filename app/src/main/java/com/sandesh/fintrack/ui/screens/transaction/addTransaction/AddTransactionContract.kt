package com.sandesh.fintrack.ui.screens.transaction.addTransaction

import android.os.Build
import androidx.annotation.RequiresApi
import com.sandesh.fintrack.common.transaction.parseDateWithCurrentTimeToMillis

sealed interface AddTransactionIntent {
    object BackClicked : AddTransactionIntent
    object SaveClicked : AddTransactionIntent

    data class TransactionTypeChanged(val isIncome: Boolean) : AddTransactionIntent
    data class AmountChanged(val amount: String) : AddTransactionIntent

    object CategoryClicked : AddTransactionIntent
    data class CategorySelected(val category: String) : AddTransactionIntent
    object DismissCategorySheet : AddTransactionIntent

    object DateClicked : AddTransactionIntent
    data class DateSelected(val date: String) : AddTransactionIntent

    data class NoteChanged(val note: String) : AddTransactionIntent
}



data class AddTransactionState(
    val isIncome: Boolean = true,
    val showCategorySheet: Boolean = false,
    val category: String = "",
    val amount: String = "",
    val availableCategories: List<String> = CategoryData.incomeCategories,
    val date: String = "",
    val note: String = "",
    val isLoading: Boolean = false,
    val showAmountError: Boolean = false,
) {
    val isAmountValid: Boolean
        get() = amount.toDoubleOrNull()?.let { it > 0 } == true

    val isFormValid: Boolean
        get() = amount.isNotBlank()
                && category.isNotBlank()
                && date.isNotBlank()

    //ADD THIS
    val dateMillis: Long
        @RequiresApi(Build.VERSION_CODES.O)
        get() = parseDateWithCurrentTimeToMillis(date)

}




sealed interface AddTransactionEffect {
    object NavigateBack : AddTransactionEffect
    object OpenDatePicker : AddTransactionEffect
    data class NavigateToSuccess(val transactionId: Long) : AddTransactionEffect

}
