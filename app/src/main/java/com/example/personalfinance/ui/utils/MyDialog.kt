package com.example.personalfinance.ui.utils

import android.app.AlertDialog
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.format.DateFormat
import android.widget.*
import androidx.annotation.NonNull
import androidx.fragment.app.DialogFragment
import com.example.personalfinance.databinding.FragmentBillDialogBinding
import com.example.personalfinance.databinding.FragmnetTransactionDialogBinding
import java.util.*


class MyDialog(private val listener : AddButtonClicked) : DialogFragment() {
    val TRANSACTION_ADD_DIALOG = "addTransaction"
    val BILL_ADD_DIALOG = "addBill"

    private lateinit var dialogBinding: FragmnetTransactionDialogBinding
    private lateinit var billDialogBinding: FragmentBillDialogBinding

    @NonNull
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        var dialog: Dialog? = null
        dialogBinding = FragmnetTransactionDialogBinding.inflate(layoutInflater)
        billDialogBinding = FragmentBillDialogBinding.inflate(layoutInflater)

        if (tag == TRANSACTION_ADD_DIALOG)
            dialog = getAddTransactionDialog()
        else if (tag == BILL_ADD_DIALOG)
            dialog = getAddBillDialog()

        dialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return dialog
    }

    private fun getAddBillDialog(): Dialog? {
        val builder: AlertDialog.Builder = AlertDialog.Builder(activity)
        builder.setView(billDialogBinding.root)
        val title: TextView = billDialogBinding.titleDialog
        val name: EditText = billDialogBinding.edt01
        val amount: EditText = billDialogBinding.edt02
        val cancel: Button = billDialogBinding.cancelBtn
        val addBtn: Button = billDialogBinding.addBtn
        val datePicker: DatePicker = billDialogBinding.datePicker
        title.text = "Add Bill"

        cancel.setOnClickListener { v -> dismiss() }

        addBtn.setOnClickListener {
            val billNameStr = name.text.toString()
            val amountInt = amount.text.toString().toInt()
            val date = getCurrentDate(datePicker)

            listener.onBillAddButtonClicked(billNameStr, amountInt, date)
        }

        return builder.create()

    }

    fun getCurrentDate(picker: DatePicker): String {
        val calendar = Calendar.getInstance()
        calendar.set(picker.year, picker.month, picker.dayOfMonth)
        return DateFormat.format("yyyy.MM.dd",calendar).toString()
    }

    private fun getAddTransactionDialog(): Dialog? {
        val builder: AlertDialog.Builder = AlertDialog.Builder(activity)
        builder.setView(dialogBinding.root)
        val title: TextView = dialogBinding.titleDialog
        val name: EditText = dialogBinding.edt01
        val amount: EditText = dialogBinding.edt02
        val cancel: Button = dialogBinding.cancelBtn
        val addBtn: Button = dialogBinding.addBtn
        val timePicker: TimePicker = dialogBinding.timePicker

        var hours: Int = 0
        var minute: Int = 0

        timePicker.setOnTimeChangedListener { timePicker, i, i2 ->
            hours = i
            minute = i2
        }

        title.text = "Add Transaction"
        cancel.setOnClickListener { v -> dismiss() }

        addBtn.setOnClickListener {

            var status = false
            if (dialogBinding.expenseRadioBtn.isChecked)
                status = false
            else if (dialogBinding.incomeRadioBtn.isChecked)
                status = true

            val nameStr = name.text.toString()
            val amountInt = amount.text.toString().toInt()

            listener.onTransactionAddButtonClicked(nameStr, amountInt, status, "$hours:$minute")
            dismiss()
        }

        return builder.create()
    }


}

interface AddButtonClicked {
    fun onTransactionAddButtonClicked(text01: String, text02: Int, status: Boolean,timeKey: String)
    fun onBillAddButtonClicked(text01: String, text02: Int, dateKey: String)
}