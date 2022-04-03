package com.example.personalfinance.repositories

import androidx.lifecycle.LiveData
import com.example.personalfinance.daos.BillDao
import com.example.personalfinance.room.billRoom.Bill

class BillRepository(private val dao: BillDao) {

    suspend fun insertBill(bill: Bill) {
        dao.insert(bill)
    }

    suspend fun deleteBill(bill: Bill) {
        dao.delete(bill)
    }

    suspend fun updateBill(bill: Bill) {
        dao.update(bill)
    }

    fun getBills(paidStatus: Boolean): LiveData<List<Bill>> {
        return dao.getBills(paidStatus)
    }
}