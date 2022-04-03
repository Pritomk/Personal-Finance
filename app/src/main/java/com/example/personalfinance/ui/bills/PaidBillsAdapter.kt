package com.example.personalfinance.ui.bills

import android.view.ContextMenu
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.personalfinance.R
import com.example.personalfinance.room.billRoom.Bill

class PaidBillsAdapter() : RecyclerView.Adapter<PaidBillViewHolder>() {
    private val paidBillsList = ArrayList<Bill>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PaidBillViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.paid_bills_item, parent, false)
        val viewHolder = PaidBillViewHolder(view)
        return viewHolder
    }

    override fun onBindViewHolder(holder: PaidBillViewHolder, position: Int) {
        val item = paidBillsList[position]
        holder.billName.text = item.billName
        holder.billAmount.text = item.amount.toString()
        holder.billDate.text = item.dateKey
    }

    override fun getItemCount(): Int {
        return paidBillsList.size
    }

    fun updateList(newList: ArrayList<Bill>) {
        paidBillsList.clear()
        paidBillsList.addAll(newList)

        notifyDataSetChanged()
    }
}

class PaidBillViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnCreateContextMenuListener {

    val billName: TextView = itemView.findViewById(R.id.paid_bill_item_name)
    val billAmount: TextView = itemView.findViewById(R.id.paid_bill_item_amount)
    val billDate: TextView = itemView.findViewById(R.id.paid_bill_date)

    init {
        itemView.setOnCreateContextMenuListener(this)
    }

    override fun onCreateContextMenu(
        p0: ContextMenu?,
        p1: View?,
        p2: ContextMenu.ContextMenuInfo?
    ) {
        p0?.add(adapterPosition,0,0,"UnPaid")
        p0?.add(adapterPosition,1,0,"Delete")

    }

}