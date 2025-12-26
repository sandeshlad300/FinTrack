package com.sandesh.fintrack.ui.screens.transaction.room

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "transactions")
data class TransactionEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val amount: Double,
    val category: String,
    val note: String,
    val isIncome: Boolean,
    val date: Long
)



