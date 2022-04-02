package com.example.personalfinance.daos

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.personalfinance.room.transactionRoom.Transaction

@Dao
interface TransactionDao {

    @Insert
    suspend fun insert(transaction: Transaction)

    @Update
    suspend fun update(transaction: Transaction)

    @Delete
    suspend fun delete(transaction: Transaction)

    @Query("Select * from transaction_table order by tid ASC")
    fun getTransactions() : LiveData<List<Transaction>>
}