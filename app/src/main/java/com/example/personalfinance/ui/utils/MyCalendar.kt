package com.example.personalfinance.ui.utils

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.text.format.DateFormat
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import java.util.*

class MyCalender(private val listener: OnCalenderClickListener) : DialogFragment() {

    private val calendar: Calendar = Calendar.getInstance()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return DatePickerDialog(
            requireActivity(),
            { view: DatePicker?, year: Int, month: Int, dayOfMonth: Int ->
                listener.onCalenderClicked(
                    year,
                    month,
                    dayOfMonth
                )
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
    }

    fun setDate(year: Int, month: Int, day: Int) {
        calendar[Calendar.YEAR] = year
        calendar[Calendar.MONTH] = month
        calendar[Calendar.DAY_OF_MONTH] = day
    }

    fun getDate(): String? {
        return DateFormat.format("dd.MM.yyyy", calendar).toString()
    }
}
interface OnCalenderClickListener {
    fun onCalenderClicked(year: Int, month: Int, day: Int)
}