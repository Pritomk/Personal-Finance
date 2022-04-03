package com.example.personalfinance.room.billRoom

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "bill_table")
data class Bill(
    @PrimaryKey(autoGenerate = true)
    var bid: Long = 0L,
    var billName: String = "",
    var amount: Int = 0,
    var dateKey: String,
    var paid: Boolean = false
)
