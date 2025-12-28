package com.sandesh.fintrack.ui.screens.transaction.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface TransactionDao {
    @Insert
    suspend fun insert(transaction: TransactionEntity): Long

    @Query("SELECT * FROM transactions WHERE id = :id")
    suspend fun getTransactionById(id: Long): TransactionEntity

    @Query("SELECT * FROM transactions ORDER BY date DESC")
    fun observeAllTransactions(): kotlinx.coroutines.flow.Flow<List<TransactionEntity>>
}
