package com.dicoding.butgetin.data.repository

import android.content.Context
import com.dicoding.butgetin.data.api.RetrofitClient
import com.dicoding.butgetin.data.model.LoginRequest
import com.dicoding.butgetin.data.model.LoginResponse
import com.dicoding.butgetin.data.model.LogoutRequest
import com.dicoding.butgetin.data.model.LogoutResponse
import com.dicoding.butgetin.data.model.RegisterRequest
import com.dicoding.butgetin.data.model.RegisterResponse
import com.dicoding.butgetin.data.model.UpdateProfileRequest
import retrofit2.Call

class UserRepository (private val context: Context) {

    fun registerUser(request: RegisterRequest): Call<RegisterResponse> {
        return RetrofitClient.instance.registerUser(request)
    }

    fun loginUser(request: LoginRequest): Call<LoginResponse> {
        return RetrofitClient.instance.loginUser(request)
    }

    fun logoutUser(token: LogoutRequest): Call<LogoutResponse> {
        return RetrofitClient.instance.logoutUser(token)
    }

    fun getUserProfile(token: String): Call<UserProfile> {
        return RetrofitClient.instance.getProfile("Bearer $token")
    }

    fun updateUserProfile(request: UpdateProfileRequest): Call<UserProfile> {
        return RetrofitClient.instance.updateUserProfile(request)
    }

    fun deleteUserProfile(): Call<Void> {
        return RetrofitClient.instance.deleteUserProfile()
    }
}