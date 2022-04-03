package com.example.personalfinance.ui.bills

import android.content.Intent
import android.os.Bundle
import android.provider.CalendarContract
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.personalfinance.databinding.FragmentBillsBinding
import com.example.personalfinance.room.billRoom.Bill
import com.example.personalfinance.ui.utils.AddButtonClicked
import com.example.personalfinance.ui.utils.MyDialog
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.*
import kotlin.collections.ArrayList


class BillsFragment : Fragment(), AddButtonClicked, BillItemClicked {

    private var _binding: FragmentBillsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var billsViewModel: BillsViewModel
    private lateinit var addFab: FloatingActionButton
    private lateinit var paidFab: FloatingActionButton
    private lateinit var dialog: MyDialog
    private lateinit var billsRecycler: RecyclerView
    private lateinit var billsAdapter: BillsAdapter
    private lateinit var billsList: ArrayList<Bill>

    private val TAG = "com.example.personalfinance.ui.bills.BillsFragment"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        billsViewModel =
            ViewModelProvider(this, BillsViewModelFactory(requireActivity().application)).get(
                BillsViewModel::class.java
            )

        _binding = FragmentBillsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        addFab = binding.addFab
        paidFab = binding.paidFab
        billsRecycler = binding.billsRecycler
        billsAdapter = BillsAdapter(this)
        setupRecycler()

        dialog = MyDialog(this)

        return root
    }

    private fun setupRecycler() {
        billsRecycler.layoutManager = LinearLayoutManager(context)
        billsRecycler.adapter = billsAdapter
        billsViewModel.allUnPaidBills.observe(viewLifecycleOwner) { list ->
            Log.d(TAG, "$list")
            Collections.sort(list, compareBy { it.dateKey })
            billsList = list as ArrayList<Bill>
            billsAdapter.updateList(list)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        addFab.setOnClickListener {
            setupDialog()
        }

        paidFab.setOnClickListener {
            startActivity(Intent(requireContext(), PaidBillsActivity::class.java))
        }

    }

    private fun setupDialog() {
        dialog.show(requireFragmentManager(), dialog.BILL_ADD_DIALOG)
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

    }

    //bills item long clicked functionality
    override fun onContextItemSelected(item: MenuItem): Boolean {

        when(item.itemId) {
            0 -> paidFunction(item.groupId)
            1 -> deleteFunction(item.groupId)
        }

        return super.onContextItemSelected(item)
    }

    private fun deleteFunction(groupId: Int) {
        billsViewModel.deleteTransaction(billsList[groupId])
        billsAdapter.notifyItemChanged(groupId)
    }

    private fun paidFunction(groupId: Int) {
        billsList[groupId].paid = true
        billsViewModel.updateBill(billsList[groupId])
        billsAdapter.notifyItemChanged(groupId)
    }



    override fun onBillAddButtonClicked(text01: String, text02: Int, datekey: String) {

        billsViewModel.insertTransaction(Bill(0, text01, text02, datekey, false))

        val intent = Intent(Intent.ACTION_INSERT);
        intent.setData(CalendarContract.Events.CONTENT_URI)
        intent.putExtra(CalendarContract.Events.TITLE, text01)
        startActivity(intent)

    }

    override fun onBillItemClicked() {

    }


}