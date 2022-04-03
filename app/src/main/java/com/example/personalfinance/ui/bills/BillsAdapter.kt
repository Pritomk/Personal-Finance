package com.example.personalfinance.ui.bills

import android.view.ContextMenu
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.personalfinance.R
import com.example.personalfinance.room.billRoom.Bill

class BillsAdapter(private val listener : BillItemClicked): RecyclerView.Adapter<BillViewHolder>() {

    private val billList = ArrayList<Bill>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BillViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.bill_item, parent, false)
        val viewHolder = BillViewHolder(view)
        return viewHolder
    }

    override fun onBindViewHolder(holder: BillViewHolder, position: Int) {
        val item = billList[position]
        holder.billName.text = item.billName
        holder.billAmount.text = item.amount.toString()
        holder.billDate.text = item.dateKey
    }

    override fun getItemCount(): Int {
        return billList.size
    }

    fun updateList(newList: ArrayList<Bill>) {
        billList.clear()
        billList.addAll(newList)

        notifyDataSetChanged()
    }
}

class BillViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnCreateContextMenuListener {

    val billName: TextView = itemView.findViewById(R.id.bill_item_name)
    val billAmount: TextView = itemView.findViewById(R.id.bill_item_amount)
    val billDate: TextView = itemView.findViewById(R.id.bill_date)

    init {
        itemView.setOnCreateContextMenuListener(this)
    }

    override fun onCreateContextMenu(
        p0: ContextMenu?,
        p1: View?,
        p2: ContextMenu.ContextMenuInfo?
    ) {
        p0?.add(adapterPosition,0,0,"Paid")
        p0?.add(adapterPosition,1,0,"Delete")

    }

}

interface BillItemClicked {
    fun onBillItemClicked()
}