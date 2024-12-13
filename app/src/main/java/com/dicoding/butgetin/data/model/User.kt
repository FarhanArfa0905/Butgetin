package com.dicoding.butgetin.data.model

import com.google.gson.annotations.SerializedName

data class User(

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("fullname")
	val fullname: String,

	@field:SerializedName("email")
	val email: String
)