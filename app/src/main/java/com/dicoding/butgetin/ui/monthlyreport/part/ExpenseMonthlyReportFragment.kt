package com.dicoding.butgetin.ui.monthlyreport.part

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.dicoding.butgetin.R
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry

class ExpenseMonthlyReportFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_expense_monthly_report, container, false)
        val pieChart: PieChart = view.findViewById(R.id.expensePieChart)

        setupPieChart(pieChart)

        return view
    }

    private fun setupPieChart(pieChart: PieChart) {
        val entries = listOf(
            PieEntry(50f, getString(R.string.education)),
            PieEntry(30f, getString(R.string.food_and_drink)),
            PieEntry(20f, getString(R.string.entertainment))
        )

        val dataSet = PieDataSet(entries, getString(R.string.expense_categories))
        dataSet.colors = listOf(
            Color.parseColor("#F44336"),
            Color.parseColor("#FF9800"),
            Color.parseColor("#03A9F4")
        )
        dataSet.valueTextColor = Color.WHITE
        dataSet.valueTextSize = 12f

        val pieData = PieData(dataSet)
        pieChart.data = pieData
        pieChart.description.isEnabled = false
        pieChart.centerText = getString(R.string.expense_chart_title)
        pieChart.setCenterTextColor(Color.BLACK)
        pieChart.setCenterTextSize(16f)
        pieChart.animateY(1000)
        pieChart.invalidate()
    }
}