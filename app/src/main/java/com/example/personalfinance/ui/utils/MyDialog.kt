package com.example.personalfinance.ui.utils

import android.app.AlertDialog
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.TimePicker
import androidx.annotation.NonNull
import androidx.fragment.app.DialogFragment
import com.example.personalfinance.databinding.FragmnetTransactionDialogBinding


class MyDialog(private val listener : AddButtonClicked) : DialogFragment() {
    val TRANSACTION_ADD_DIALOG = "addTransaction"
    private lateinit var dialogBinding: FragmnetTransactionDialogBinding

    @NonNull
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        var dialog: Dialog? = null
        dialogBinding = FragmnetTransactionDialogBinding.inflate(layoutInflater)

        if (tag == TRANSACTION_ADD_DIALOG)
            dialog = getAddTransactionDialog()

        dialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return dialog
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
}