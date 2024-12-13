package com.dicoding.butgetin.data.repository

import com.dicoding.butgetin.data.api.RetrofitClient
import com.dicoding.butgetin.data.model.RecommendationRequest
import com.dicoding.butgetin.data.model.RecommendationResponse
import retrofit2.Call

class RecommendationRepository {
    fun getRecommendation(userId: Int): Call<RecommendationResponse> {
        val request = RecommendationRequest(userId)
        return RetrofitClient.instance.getRecommendation(request)
    }
}

