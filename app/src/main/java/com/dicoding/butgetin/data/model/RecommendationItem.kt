package com.dicoding.butgetin.data.model

import com.google.gson.annotations.SerializedName

data class RecommendationItem(

	@field:SerializedName("category")
	val category: String,

	@field:SerializedName("budget_recommendation")
	val budgetRecommendation: Int
)