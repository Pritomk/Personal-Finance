package com.example.personalfinance.ui.stats

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.personalfinance.databinding.FragmentStatsBinding
import im.dacer.androidcharts.BarView

private val TAG = "com.example.personalfinance.ui.stats.StatsFragment"


class StatsFragment : Fragment() {

    private var _binding: FragmentStatsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var barView: BarView
    private lateinit var incomeBarView: BarView
    private lateinit var statsViewModel: StatsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        statsViewModel =
            ViewModelProvider(this,StatusViewModelFactory(requireActivity().application))[StatsViewModel::class.java]

        _binding = FragmentStatsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        barView = binding.barView
        incomeBarView = binding.incomeBarView
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        expenseDataset()

        incomeDataset()
    }

    private fun incomeDataset() {
        statsViewModel.allTransactions.observe(viewLifecycleOwner) { list ->
            val dateSets = HashMap<String, Int>()
            for (i in 0 until list.size) {
                val item = list[i]
                if (item.status) {
                    Log.d(TAG, "$item")
                    if (dateSets.containsKey(item.dateKey)) {
                        val amount = dateSets.get(item.dateKey)!!
                        dateSets[item.dateKey] = amount + item.amount
                    } else {
                        dateSets[item.dateKey] = item.amount
                    }
                }
                Log.d(TAG, "$dateSets")
            }
            val iterator = dateSets.entries.iterator()
            val xaxis = ArrayList<String>()
            val yaxis = ArrayList<Int>()
            while (iterator.hasNext()) {
                val ele = iterator.next()
                xaxis.add(ele.key)
                yaxis.add(ele.value)
            }
            incomeBarView.setBottomTextList(xaxis)
            incomeBarView.setDataList(yaxis,5000)


        }
    }

    private fun expenseDataset() {
        statsViewModel.allTransactions.observe(viewLifecycleOwner) { list ->
            val dateSets = HashMap<String, Int>()
            for (i in 0 until list.size) {
                val item = list[i]
                if (!item.status) {
                    if (dateSets.containsKey(item.dateKey)) {
                        val amount = dateSets.get(item.dateKey)!!
                        dateSets[item.dateKey] = amount + item.amount
                    } else {
                        dateSets[item.dateKey] = item.amount
                    }
                }
            }
            val iterator = dateSets.entries.iterator()
            val xaxis = ArrayList<String>()
            val yaxis = ArrayList<Int>()
            while (iterator.hasNext()) {
                val ele = iterator.next()
                xaxis.add(ele.key)
                yaxis.add(ele.value)
            }
            Log.d(TAG, "$xaxis")
            barView.setBottomTextList(xaxis)
            barView.setDataList(yaxis,5000)


        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}