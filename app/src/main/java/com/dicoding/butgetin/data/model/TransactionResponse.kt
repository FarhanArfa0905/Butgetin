package com.dicoding.butgetin.data.model

import com.google.gson.annotations.SerializedName

data class TransactionResponse(

	@field:SerializedName("date")
	val date: String,

	@field:SerializedName("familyId")
	val familyId: Int,

	@field:SerializedName("createdAt")
	val createdAt: String,

	@field:SerializedName("amount")
	val amount: Int,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("category")
	val category: String,

	@field:SerializedName("type")
	val type: String,

	@field:SerializedName("userId")
	val userId: Int,

	@field:SerializedName("updatedAt")
	val updatedAt: String,
)