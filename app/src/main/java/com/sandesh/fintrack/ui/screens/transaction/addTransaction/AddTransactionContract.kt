package com.sandesh.fintrack.ui.screens.transaction.addTransaction

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

}



sealed interface AddTransactionEffect {
    object NavigateBack : AddTransactionEffect
    object SaveTransaction : AddTransactionEffect
    object OpenDatePicker : AddTransactionEffect
}
