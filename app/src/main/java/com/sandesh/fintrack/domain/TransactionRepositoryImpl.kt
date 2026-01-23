package com.sandesh.fintrack.domain

import com.sandesh.fintrack.mappper.toDomain
import com.sandesh.fintrack.mappper.toEntity
import com.sandesh.fintrack.mappper.toModel
import com.sandesh.fintrack.ui.screens.transaction.room.TransactionDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TransactionRepositoryImpl(
    private val dao: TransactionDao
) : TransactionRepository {

    override suspend fun insert(transaction: TransactionModel): Long {
        return dao.insert(transaction.toEntity())
    }

    override suspend fun getTransactionById(id: Long): TransactionModel {
        return dao.getTransactionById(id).toDomain()
    }

    override fun observeTransactions(): Flow<List<TransactionModel>> {
        return dao.observeAllTransactions()
            .map { list ->
                list.map { it.toModel() }
            }
    }


    fun totalIncome(): Flow<Double> = dao.getTotalIncome()

    fun totalExpense(): Flow<Double> = dao.getTotalExpense()

    fun totalBalance(): Flow<Double> = dao.getTotalBalance()
}