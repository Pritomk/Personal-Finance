package com.example.personalfinance.ui.trans

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.personalfinance.repositories.TransactionRepository
import com.example.personalfinance.room.transactionRoom.Transaction
import com.example.personalfinance.room.transactionRoom.TransactionDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TransactionViewModel(private val application: Application) : ViewModel() {

    private val repository: TransactionRepository
    val allTransactions : LiveData<List<Transaction>>

    init {
        val dao = TransactionDatabase.getDatabase(application).getTransactionDao()
        repository = TransactionRepository(dao)
        allTransactions = repository.getTransactions()
    }

    //Insert transaction in room
    fun insertTransaction(transaction: Transaction) = viewModelScope.launch(Dispatchers.IO) {
        repository.insertTransaction(transaction)
    }

    fun deleteTransaction(transaction: Transaction) = viewModelScope.launch(Dispatchers.IO) {
        repository.deleteTransaction(transaction)
    }

    fun dateTransactions(dateKey: String) : LiveData<List<Transaction>> {
        return repository.getDateTransactions(dateKey)
    }

}