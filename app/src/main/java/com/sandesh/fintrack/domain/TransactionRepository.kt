package com.sandesh.fintrack.domain

import kotlinx.coroutines.flow.Flow


interface TransactionRepository {
    suspend fun insert(transaction: TransactionModel): Long
    suspend fun getTransactionById(id: Long): TransactionModel
    fun observeTransactions(): Flow<List<TransactionModel>>
}

