package com.sandesh.fintrack.domain


data class TransactionModel(
    val id: Long,
    val amount: Double,
    val category: String,
    val note: String,
    val isIncome: Boolean,
    val date: Long,

)
