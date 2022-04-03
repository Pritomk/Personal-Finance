package com.example.personalfinance.repositories

import androidx.lifecycle.LiveData
import com.example.personalfinance.daos.TransactionDao
import com.example.personalfinance.room.transactionRoom.Transaction

class TransactionRepository(private val dao: TransactionDao) {

    suspend fun insertTransaction(transaction: Transaction) {
        dao.insert(transaction)
    }

    suspend fun deleteTransaction(transaction: Transaction) {
        dao.delete(transaction)
    }

    fun getTransactions() : LiveData<List<Transaction>> {
        return dao.getTransactions()
    }

    fun getDateTransactions(dateKey: String) : LiveData<List<Transaction>> {
        return dao.getDateTransactions(dateKey)
    }

}