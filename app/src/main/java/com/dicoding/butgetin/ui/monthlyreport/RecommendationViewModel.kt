package com.dicoding.butgetin.ui.monthlyreport

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.butgetin.data.model.RecommendationResponse
import com.dicoding.butgetin.data.repository.RecommendationRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RecommendationViewModel(private val repository: RecommendationRepository) : ViewModel() {

    private val _recommendationLiveData = MutableLiveData<RecommendationResponse>()
    val recommendationLiveData: LiveData<RecommendationResponse> = _recommendationLiveData

    private val _errorLiveData = MutableLiveData<String>()
    val errorLiveData: LiveData<String> = _errorLiveData

    fun getRecommendation(userId: Int) {
        repository.getRecommendation(userId).enqueue(object : Callback<RecommendationResponse> {
            override fun onResponse(
                call: Call<RecommendationResponse>,
                response: Response<RecommendationResponse>
            ) {
                if (response.isSuccessful) {
                    _recommendationLiveData.postValue(response.body())
                } else {
                    _errorLiveData.postValue("Gagal mendapatkan rekomendasi: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<RecommendationResponse>, t: Throwable) {
                _errorLiveData.postValue("Terjadi kesalahan: ${t.message}")
            }
        })
    }
}