package com.example.personalfinance.ui.trans

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextClock
import android.widget.TextView
import android.widget.TimePicker
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.personalfinance.databinding.FragmentTransactionBinding
import com.example.personalfinance.room.transactionRoom.Transaction
import com.example.personalfinance.ui.bills.BillsViewModel
import com.example.personalfinance.ui.utils.AddButtonClicked
import com.example.personalfinance.ui.utils.MyCalender
import com.example.personalfinance.ui.utils.MyDialog
import com.example.personalfinance.ui.utils.OnCalenderClickListener
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.*
import kotlin.collections.ArrayList

class TransactionFragment : Fragment(), AddButtonClicked, OnCalenderClickListener,
    TransactionItemClicked {

    private var _binding: FragmentTransactionBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var dialog: MyDialog
    private lateinit var transactionViewModel: TransactionViewModel
    private lateinit var fab: FloatingActionButton
    private lateinit var calendar: MyCalender
    private lateinit var transactionAdapter: TransactionAdapter
    private lateinit var transactionRecyclerView: RecyclerView


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        transactionViewModel =
            ViewModelProvider(this, TransactionViewModelFactory(requireActivity().application)).get(TransactionViewModel::class.java)

        _binding = FragmentTransactionBinding.inflate(inflater, container, false)
        val root: View = binding.root

        dialog = MyDialog(this)
        fab = binding.fab
        calendar = MyCalender(this)
        transactionAdapter = TransactionAdapter(this)
        transactionRecyclerView = binding.transRecycler

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fab.setOnClickListener {
            setupDialog()
        }

        setupTransactionRecycler()

    }

    private fun setupTransactionRecycler() {
        transactionRecyclerView.adapter = transactionAdapter
        transactionRecyclerView.layoutManager = LinearLayoutManager(activity)
        transactionViewModel.allTransactions.observe(viewLifecycleOwner) {
            transactionAdapter.updateList(it as ArrayList<Transaction>)
        }
    }

    private fun setupDialog() {
        dialog.show(requireFragmentManager(), dialog.TRANSACTION_ADD_DIALOG)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onTransactionAddButtonClicked(
        text01: String,
        text02: Int,
        status: Boolean,
        timeKey: String
    ) {
        transactionViewModel.insertTransaction(Transaction(0,text01,text02,status,calendar.getDate()!!, timeKey))

    }

    override fun onBillAddButtonClicked(text01: String, text02: Int, dateKey: String) {

    }

    override fun onCalenderClicked(year: Int, month: Int, day: Int) {
        calendar.setDate(year, month, day)
    }

    override fun onTransactionItemClicked() {

    }


}