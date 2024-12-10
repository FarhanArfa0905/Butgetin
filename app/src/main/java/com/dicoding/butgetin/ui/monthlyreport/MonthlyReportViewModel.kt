package com.dicoding.butgetin.ui.monthlyreport

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MonthlyReportViewModel : ViewModel() {

    private val _transactions = MutableLiveData<List<String>>()
    val transactions: LiveData<List<String>> get() = _transactions

    init {
        _transactions.value = emptyList()
    }

    fun updateTransactions(newTransactions: List<String>) {
        _transactions.value = newTransactions
    }
}