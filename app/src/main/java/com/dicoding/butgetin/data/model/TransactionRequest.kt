package com.dicoding.butgetin.data.model

import com.google.gson.annotations.SerializedName

data class TransactionRequest(

	@field:SerializedName("date")
	val date: String,

	@field:SerializedName("familyId")
	val familyId: Int,

	@field:SerializedName("amount")
	val amount: Double?,

	@field:SerializedName("category")
	val category: String,

	@field:SerializedName("type")
	val type: String,

	@field:SerializedName("userId")
	val userId: Int
)