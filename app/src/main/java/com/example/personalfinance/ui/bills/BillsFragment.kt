package com.example.personalfinance.ui.bills

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.personalfinance.databinding.FragmentBillsBinding
import com.example.personalfinance.room.transactionRoom.Transaction
import com.example.personalfinance.ui.utils.AddButtonClicked
import com.example.personalfinance.ui.utils.MyDialog
import com.google.android.material.floatingactionbutton.FloatingActionButton

class BillsFragment : Fragment(), AddButtonClicked {

    private var _binding: FragmentBillsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var dialog: MyDialog
    private lateinit var billsViewModel: BillsViewModel
    private lateinit var fab: FloatingActionButton

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        billsViewModel =
            ViewModelProvider(this,BillsViewModelFactory(requireActivity().application)).get(BillsViewModel::class.java)

        _binding = FragmentBillsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        dialog = MyDialog(this)
        fab = binding.fab!!

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fab.setOnClickListener {
            setupDialog()
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

    }
}