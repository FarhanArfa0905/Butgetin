package com.dicoding.butgetin.data.model

import com.google.gson.annotations.SerializedName

data class RecommendationResponse(

	@field:SerializedName("income")
	val income: Int,

	@field:SerializedName("totalExpenses")
	val totalExpenses: Int,

	@field:SerializedName("recommendation")
	val recommendation: List<RecommendationItem>
)