package com.dicoding.butgetin.ui.tracking

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.butgetin.api.Transaction

class TransactionViewModel : ViewModel() {
    private val _transactions = MutableLiveData<List<Transaction>>(emptyList())
    val transactions: LiveData<List<Transaction>> get() = _transactions

    fun addTransaction(transaction: Transaction) {
        val currentList = _transactions.value?.toMutableList() ?: mutableListOf()
        currentList.add(transaction)
        _transactions.value = currentList
    }
}