package com.example.personalfinance.daos

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.personalfinance.room.billRoom.Bill

@Dao
interface BillDao {

    @Insert
    suspend fun insert(bill: Bill)

    @Delete
    suspend fun delete(bill: Bill)

    @Update
    suspend fun update(bill: Bill)

    @Query("Select * from bill_table where paid = :paidStatus")
    fun getBills(paidStatus: Boolean) : LiveData<List<Bill>>
}