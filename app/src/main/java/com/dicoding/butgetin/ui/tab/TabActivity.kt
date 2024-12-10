package com.dicoding.butgetin.ui.tab

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.dicoding.butgetin.R
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class TabActivity : AppCompatActivity() {
    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager: ViewPager2
    private lateinit var adapter: TabAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tab)

        tabLayout = findViewById(R.id.tabLayout)
        viewPager = findViewById(R.id.viewPager)

        val isMonthlyReport = intent.getBooleanExtra("isMonthlyReport", false)
        val selectedTab = intent.getIntExtra("selected_tab", 0)

        adapter = TabAdapter(this, isMonthlyReport)
        viewPager.adapter = adapter

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = if (position == 0) getString(R.string.income) else getString(R.string.expense)
        }.attach()

        viewPager.setCurrentItem(selectedTab, false)

        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                val colorResId = if (position == 0) R.color.income else R.color.expense
                tabLayout.setSelectedTabIndicatorColor(getColor(colorResId))
            }
        })
    }

    fun getCurrentFragment(): Fragment? {
        return supportFragmentManager.findFragmentByTag("f${viewPager.currentItem}")
    }
}