package com.dicoding.butgetin.data.repository

import com.dicoding.butgetin.data.api.RetrofitClient

class TransactionRepository {

    private val apiService = RetrofitClient.instance

    suspend fun getTransactionReport(id: String, startDate: String, endDate: String) =
        apiService.getTransactionReport(id, startDate, endDate)

    suspend fun exportTransactions(id: String, startDate: String, endDate: String) =
        apiService.exportTransactions(id, startDate, endDate)
}
