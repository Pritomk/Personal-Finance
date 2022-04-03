package com.example.personalfinance.ui.bills

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.personalfinance.repositories.BillRepository
import com.example.personalfinance.repositories.TransactionRepository
import com.example.personalfinance.room.billRoom.Bill
import com.example.personalfinance.room.billRoom.BillDatabase
import com.example.personalfinance.room.transactionRoom.Transaction
import com.example.personalfinance.room.transactionRoom.TransactionDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BillsViewModel(application: Application) : ViewModel() {

    private val repository: BillRepository
    val allUnPaidBills : LiveData<List<Bill>>
    val allPaidBills: LiveData<List<Bill>>

    init {
        val dao = BillDatabase.getDatabase(application).getBillDao()
        repository = BillRepository(dao)
        allUnPaidBills = repository.getBills(false)
        allPaidBills = repository.getBills(true)
    }

    //Insert transaction in room
    fun insertTransaction(bill: Bill) = viewModelScope.launch(Dispatchers.IO) {
        repository.insertBill(bill)
    }

    fun deleteTransaction(bill: Bill) = viewModelScope.launch(Dispatchers.IO) {
        repository.deleteBill(bill)
    }

    fun updateBill(bill: Bill) = viewModelScope.launch(Dispatchers.IO) {
        repository.updateBill(bill)
    }
}