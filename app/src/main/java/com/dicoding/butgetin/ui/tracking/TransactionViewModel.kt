package com.dicoding.butgetin.ui.tracking

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.butgetin.data.api.ApiService
import com.dicoding.butgetin.data.model.TransactionRequest
import com.dicoding.butgetin.data.model.TransactionResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TransactionViewModel(private val apiService: ApiService) : ViewModel() {

    private val _transactions = MutableLiveData<List<TransactionResponse>>(emptyList())
    val transactions: LiveData<List<TransactionResponse>> get() = _transactions

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    fun fetchTransactions() {
        _isLoading.value = true
        apiService.getTransactions().enqueue(object : Callback<List<TransactionResponse>> {
            override fun onResponse(
                call: Call<List<TransactionResponse>>,
                response: Response<List<TransactionResponse>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _transactions.value = response.body() ?: emptyList()
                } else {
                    _transactions.value = emptyList()
                }
            }

            override fun onFailure(call: Call<List<TransactionResponse>>, t: Throwable) {
                _isLoading.value = false
            }
        })
    }

    fun addTransaction(transactionRequest: TransactionRequest) {
        _isLoading.value = true
        apiService.postTransaction(transactionRequest).enqueue(object : Callback<TransactionResponse> {
            override fun onResponse(
                call: Call<TransactionResponse>,
                response: Response<TransactionResponse>
            ) {
                _isLoading.value = false

                if (response.isSuccessful) {
                    fetchTransactions()
                }
            }

            override fun onFailure(call: Call<TransactionResponse>, t: Throwable) {
                _isLoading.value = false
            }
        })
    }

    fun updateTransaction(id: Int, transactionRequest: TransactionRequest) {
        _isLoading.value = true
        apiService.updateTransaction(id, transactionRequest).enqueue(object : Callback<TransactionResponse> {
            override fun onResponse(
                call: Call<TransactionResponse>,
                response: Response<TransactionResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    // Tambahkan log untuk memastikan transaksi berhasil diupdate
                    Log.d("TransactionViewModel", "Transaction updated successfully: ${response.body()}")
                    fetchTransactions()
                } else {
                    Log.d("TransactionViewModel", "Failed to update transaction")
                }
            }

            override fun onFailure(call: Call<TransactionResponse>, t: Throwable) {
                _isLoading.value = false
                Log.d("TransactionViewModel", "Failed to update transaction: ${t.message}")
            }
        })
    }


    fun deleteTransaction(id: Int) {
        _isLoading.value = true
        apiService.deleteTransaction(id).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    fetchTransactions()
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                _isLoading.value = false
            }
        })
    }
}