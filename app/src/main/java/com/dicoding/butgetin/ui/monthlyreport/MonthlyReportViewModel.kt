package com.dicoding.butgetin.ui.monthlyreport

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MonthlyReportViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is Monthly Report Fragment"
    }
    val text: LiveData<String> = _text
}