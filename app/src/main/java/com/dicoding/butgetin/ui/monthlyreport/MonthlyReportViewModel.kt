package com.dicoding.butgetin.ui.monthlyreport

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.butgetin.data.repository.TransactionRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream

class MonthlyReportViewModel : ViewModel() {

    private val repository = TransactionRepository()

    fun fetchTransactionReport(id: String, startDate: String, endDate: String) {
        viewModelScope.launch {
            val response = repository.getTransactionReport(id, startDate, endDate)
            if (response.isSuccessful) {
                // Gunakan data response.body()
            } else {
                // Tangani error
            }
        }
    }

    fun exportTransaction(id: String, startDate: String, endDate: String, filePath: File) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = repository.exportTransactions(id, startDate, endDate)
                if (response.isSuccessful && response.body() != null) {
                    val excelFile = response.body()?.byteStream()
                    saveFileToStorage(excelFile, filePath)
                } else {
                    Log.e("API_ERROR", "Failed: ${response.code()}")
                }
            } catch (e: Exception) {
                Log.e("API_EXCEPTION", e.message.toString())
            }
        }
    }

    private fun saveFileToStorage(inputStream: InputStream?, filePath: File) {
        try {
            inputStream?.let {
                val outputStream = FileOutputStream(filePath)
                it.copyTo(outputStream) // Menggunakan copyTo langsung
                outputStream.close()
                Log.i("FILE_SAVED", "File saved to ${filePath.absolutePath}")
            }
        } catch (e: Exception) {
            Log.e("FILE_SAVE_ERROR", e.message.toString())
        }
    }
}