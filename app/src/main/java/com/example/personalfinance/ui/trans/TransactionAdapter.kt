package com.example.personalfinance.ui.trans

import android.content.Context
import android.view.ContextMenu
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.personalfinance.R
import com.example.personalfinance.room.transactionRoom.Transaction

class TransactionAdapter(private val listener: TransactionItemClicked): RecyclerView.Adapter<TransactionViewHolder>() {

    private val transactionList = ArrayList<Transaction>()
    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.transaction_item, parent, false)
        val viewHolder = TransactionViewHolder(view)
        context = parent.context

        return viewHolder
    }

    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
        val item = transactionList[position]
        holder.transName.text = item.name
        holder.amount.text = item.amount.toString()
        holder.transTime.text = item.timeKey

        if (item.status) {
            Glide.with(context).load(R.drawable.ic_up_arrow).into(holder.statusImage)
        } else {
            Glide.with(context).load(R.drawable.ic_down_arrow).into(holder.statusImage)
        }
    }

    override fun getItemCount(): Int {
        return transactionList.size
    }

    fun updateList(newList: ArrayList<Transaction>) {
        transactionList.clear()
        transactionList.addAll(newList)

        notifyDataSetChanged()
    }

}

class TransactionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),View.OnCreateContextMenuListener {

    val transName: TextView = itemView.findViewById(R.id.transaction_item_name)
    val amount: TextView = itemView.findViewById(R.id.transaction_item_amount)
    val statusImage : ImageView = itemView.findViewById(R.id.transaction_item_status)
    val transTime: TextView = itemView.findViewById(R.id.transaction_item_time)

    override fun onCreateContextMenu(
        p0: ContextMenu?,
        p1: View?,
        p2: ContextMenu.ContextMenuInfo?
    ) {
        p0?.add(adapterPosition,0,0,"Edit")
        p0?.add(adapterPosition,1,0,"Delete")
    }

}

interface TransactionItemClicked {
    fun onTransactionItemClicked()
}