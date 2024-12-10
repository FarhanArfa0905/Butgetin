package com.dicoding.butgetin.ui.tab

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.dicoding.butgetin.ui.monthlyreport.part.ExpenseMonthlyReportFragment
import com.dicoding.butgetin.ui.monthlyreport.part.IncomeMonthlyReportFragment
import com.dicoding.butgetin.ui.tracking.part.ExpenseTrackingFragment
import com.dicoding.butgetin.ui.tracking.part.IncomeTrackingFragment

class TabAdapter(fragmentActivity: FragmentActivity, private val isMonthlyReport: Boolean) : FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return if (isMonthlyReport) {
            when (position) {
                0 -> IncomeMonthlyReportFragment()
                else -> ExpenseMonthlyReportFragment()
            }
        } else {
            when (position) {
                0 -> IncomeTrackingFragment()
                else -> ExpenseTrackingFragment()
            }
        }
    }
}