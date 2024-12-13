package com.dicoding.butgetin.data.api

import com.dicoding.butgetin.data.model.GoogleCallbackResponse
import com.dicoding.butgetin.data.model.GoogleLoginResponse
import com.dicoding.butgetin.data.model.LoginRequest
import com.dicoding.butgetin.data.model.LoginResponse
import com.dicoding.butgetin.data.model.LogoutRequest
import com.dicoding.butgetin.data.model.LogoutResponse
import com.dicoding.butgetin.data.model.RecommendationRequest
import com.dicoding.butgetin.data.model.RecommendationResponse
import com.dicoding.butgetin.data.model.RegisterRequest
import com.dicoding.butgetin.data.model.RegisterResponse
import com.dicoding.butgetin.data.model.TransactionReportResponse
import com.dicoding.butgetin.data.model.TransactionRequest
import com.dicoding.butgetin.data.model.TransactionResponse
import com.dicoding.butgetin.data.model.UpdateProfileRequest
import com.dicoding.butgetin.data.repository.UserProfile
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @POST("auth/register")
    fun registerUser(@Body request: RegisterRequest): Call<RegisterResponse>

    @POST("auth/login")
    fun loginUser(@Body request: LoginRequest): Call<LoginResponse>

    @POST("auth/logout")
    fun logoutUser(@Body token: LogoutRequest): Call<LogoutResponse>

    @GET("auth/google")
    fun loginWithGoogle(): Call<GoogleLoginResponse>

    @GET("auth/google/callback")
    fun googleCallback(): Call<GoogleCallbackResponse>

    @GET("api/profile")
    fun getProfile(
        @Header("Authorization") token: String
    ): Call<UserProfile>

    @PUT("api/profile")
    fun updateUserProfile(@Body request: UpdateProfileRequest): Call<UserProfile>

    @DELETE("api/profile")
    fun deleteUserProfile(): Call<Void>

    @POST("api/transaction")
    fun postTransaction(
        @Body transactionRequest: TransactionRequest
    ): Call<TransactionResponse>

    @GET("api/transactions")
    fun getTransactions(): Call<List<TransactionResponse>>

    @PUT("api/transaction/{id}")
    fun updateTransaction(
        @Path("id") id: Int,
        @Body updateRequest: TransactionRequest
    ): Call<TransactionResponse>

    @DELETE("api/transaction/{id}")
    fun deleteTransaction(
        @Path("id") id: Int
    ): Call<Void>

    @GET("api/transaction-report")
    suspend fun getTransactionReport(
        @Query("id") id: String,
        @Query("startDate") startDate: String,
        @Query("endDate") endDate: String
    ): Response<TransactionReportResponse>

    @GET("exportTransactions")
    suspend fun exportTransactions(
        @Query("id") id: String,
        @Query("startDate") startDate: String,
        @Query("endDate") endDate: String
    ): Response<ResponseBody>

    @POST("api/recommendation")
    fun getRecommendation(
        @Body request: RecommendationRequest
    ): Call<RecommendationResponse>
}