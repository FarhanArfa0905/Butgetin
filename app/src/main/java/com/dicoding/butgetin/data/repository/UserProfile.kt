package com.dicoding.butgetin.data.repository

import com.google.gson.annotations.SerializedName

data class UserProfile(

	@field:SerializedName("googleId")
	val googleId: String? = null,

	@field:SerializedName("familyId")
	val familyId: Int?,

	@field:SerializedName("createdAt")
	val createdAt: String,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("fullname")
	val fullname: String,

	@field:SerializedName("avatar")
	val avatar: String?,

	@field:SerializedName("family")
	val family: List<FamilyMember>?,

	@field:SerializedName("email")
	val email: String,

	@field:SerializedName("updatedAt")
	val updatedAt: String
)

data class FamilyMember(
	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("relation")
	val relation: String
)