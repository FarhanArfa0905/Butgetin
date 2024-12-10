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

class IncomeMonthlyReportFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_income_monthly_report, container, false)
        val pieChart: PieChart = view.findViewById(R.id.incomePieChart)

        setupPieChart(pieChart)

        return view
    }

    private fun setupPieChart(pieChart: PieChart) {
        val entries = listOf(
            PieEntry(60f, getString(R.string.salary)),
            PieEntry(25f, getString(R.string.allowance)),
            PieEntry(15f, getString(R.string.grants))
        )

        val dataSet = PieDataSet(entries, getString(R.string.income_categories))
        dataSet.colors = listOf(
            Color.parseColor("#4CAF50"),
            Color.parseColor("#FFC107"),
            Color.parseColor("#FF5722")
        )
        dataSet.valueTextColor = Color.WHITE
        dataSet.valueTextSize = 12f

        val pieData = PieData(dataSet)
        pieChart.data = pieData
        pieChart.description.isEnabled = false
        pieChart.centerText = getString(R.string.income_chart_title)
        pieChart.setCenterTextColor(Color.BLACK)
        pieChart.setCenterTextSize(16f)
        pieChart.animateY(1000)
        pieChart.invalidate()
    }
}