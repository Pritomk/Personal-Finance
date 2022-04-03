package com.example.personalfinance.ui.stats

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.personalfinance.repositories.TransactionRepository
import com.example.personalfinance.room.transactionRoom.Transaction
import com.example.personalfinance.room.transactionRoom.TransactionDatabase

class StatsViewModel(private val application: Application) : ViewModel() {

    private val repository: TransactionRepository
    val allTransactions : LiveData<List<Transaction>>

    init {
        val dao = TransactionDatabase.getDatabase(application).getTransactionDao()
        repository = TransactionRepository(dao)
        allTransactions = repository.getTransactions()
    }

    fun dateTransactions(dateKey: String) : LiveData<List<Transaction>> {
        return repository.getDateTransactions(dateKey)
    }


}