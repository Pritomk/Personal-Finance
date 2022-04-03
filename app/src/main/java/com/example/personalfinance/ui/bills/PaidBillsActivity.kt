package com.example.personalfinance.ui.bills

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.personalfinance.databinding.ActivityPaidBillsBinding
import com.example.personalfinance.room.billRoom.Bill
import java.util.*
import kotlin.collections.ArrayList

class PaidBillsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPaidBillsBinding
    private lateinit var paidBillsAdapter: PaidBillsAdapter
    private lateinit var paidBillsRecyclerView: RecyclerView
    private lateinit var billsViewModel: BillsViewModel
    private lateinit var paidBillsList: ArrayList<Bill>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPaidBillsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        billsViewModel = ViewModelProvider(this, BillsViewModelFactory(application))[BillsViewModel::class.java]

        paidBillsRecyclerView = binding.paidBillRecycler
        paidBillsAdapter = PaidBillsAdapter()

        paidBillsRecyclerView.adapter = paidBillsAdapter
        paidBillsRecyclerView.layoutManager = LinearLayoutManager(this)

        billsViewModel.allPaidBills.observe(this) { list ->
            paidBillsList = list as ArrayList<Bill>
            Collections.sort(list, compareBy { it.dateKey })
            paidBillsAdapter.updateList(list)

        }
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {

        when(item.itemId) {
            0 -> unpaidFunction(item.groupId)
            1 -> deleteFunction(item.groupId)
        }

        return super.onContextItemSelected(item)
    }

    private fun deleteFunction(groupId: Int) {
        billsViewModel.deleteTransaction(paidBillsList[groupId])
        paidBillsAdapter.notifyItemChanged(groupId)
    }

    private fun unpaidFunction(groupId: Int) {
        paidBillsList[groupId].paid = false
        billsViewModel.updateBill(paidBillsList[groupId])

        paidBillsAdapter.notifyItemChanged(groupId)
    }
}