package com.dicoding.butgetin.data.model

import com.google.gson.annotations.SerializedName

data class RegisterRequest(

	@field:SerializedName("fullname")
	val fullname: String,

	@field:SerializedName("email")
	val email: String,

	@field:SerializedName("password")
	val password: String,

	@field:SerializedName("confirmPassword")
	val confirmPassword: String
)

data class GoogleLoginResponse(
	val url: String
)

data class GoogleCallbackResponse(
	val token: String,
	val message: String
)