package com.sandesh.fintrack.mappper

import com.sandesh.fintrack.domain.TransactionModel
import com.sandesh.fintrack.ui.screens.transaction.room.TransactionEntity
import com.sandesh.fintrack.ui.screens.transaction.transactionSuccess.TransactionUiModel

fun TransactionEntity.toDomain(): TransactionModel =
    TransactionModel(id, amount, category, note, isIncome, date)

fun TransactionModel.toEntity(): TransactionEntity =
    TransactionEntity(id, amount, category, note, isIncome, date)

fun TransactionEntity.toModel() = TransactionModel(
    id = id,
    amount = amount,
    category = category,
    note = note,
    isIncome = isIncome,
    date = date
)


fun TransactionModel.toUi(): TransactionUiModel {
    return TransactionUiModel(
        amount = amount,
        category = category,
        source = if (isIncome) "Income" else "Expense",
        date = date
    )
}

