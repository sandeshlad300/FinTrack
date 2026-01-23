package com.sandesh.fintrack.ui.screens.transaction.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionDao {
    @Insert
    suspend fun insert(transaction: TransactionEntity): Long

    @Query("SELECT * FROM transactions WHERE id = :id")
    suspend fun getTransactionById(id: Long): TransactionEntity

    @Query("SELECT * FROM transactions ORDER BY date DESC")
    fun observeAllTransactions(): kotlinx.coroutines.flow.Flow<List<TransactionEntity>>



    @Query("""
        SELECT IFNULL(SUM(amount), 0) 
        FROM transactions 
        WHERE isIncome = 1
    """)
    fun getTotalIncome(): Flow<Double>

    @Query("""
        SELECT IFNULL(SUM(amount), 0) 
        FROM transactions 
        WHERE isIncome = 0
    """)
    fun getTotalExpense(): Flow<Double>

    // 🔥 CRITICAL FIX
    @Query("""
        SELECT IFNULL(
            SUM(
                CASE 
                    WHEN isIncome = 1 THEN amount
                    ELSE -amount
                END
            ), 
        0)
        FROM transactions
    """)
    fun getTotalBalance(): Flow<Double>


}
