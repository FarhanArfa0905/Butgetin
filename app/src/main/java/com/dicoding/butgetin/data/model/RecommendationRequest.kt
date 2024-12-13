package com.dicoding.butgetin.data.model

import com.google.gson.annotations.SerializedName

data class RecommendationRequest(

	@field:SerializedName("userId")
	val userId: Int
)