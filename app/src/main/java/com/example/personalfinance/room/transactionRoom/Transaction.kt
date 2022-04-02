package com.example.personalfinance.room.transactionRoom

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "transaction_table")
class Transaction (
    @PrimaryKey(autoGenerate = true)
    var tid: Long = 0L,
    var name: String = "",
    var amount: Int = 0,
    var status: Boolean = false,
    var dateKey: String = "",
    var timeKey: String = ""
)