package com.dicoding.butgetin.ui.profile

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.butgetin.data.repository.UserProfile
import com.dicoding.butgetin.data.repository.UserRepository
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserProfileViewModel(private val userRepository: UserRepository) : ViewModel() {

    private val _userProfile = MutableLiveData<UserProfile>()
    val userProfile: LiveData<UserProfile> get() = _userProfile

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

    private val TAG = "UserProfileViewModel" // Tag for logging

    fun fetchUserProfile(token: String) {
        Log.d(TAG, "Fetching user profile with token: $token") // Log the start of the request

        viewModelScope.launch {
            userRepository.getUserProfile(token).enqueue(object : Callback<UserProfile> {
                override fun onResponse(call: Call<UserProfile>, response: Response<UserProfile>) {
                    if (response.isSuccessful) {
                        Log.d(TAG, "Profile fetched successfully: ${response.body()}") // Log successful response
                        _userProfile.value = response.body()
                    } else {
                        Log.e(TAG, "Failed to load profile: ${response.message()}") // Log failure message
                        _errorMessage.value = "Failed to load profile: ${response.message()}"
                    }
                }

                override fun onFailure(call: Call<UserProfile>, t: Throwable) {
                    Log.e(TAG, "Error fetching profile: ${t.message}", t) // Log error
                    _errorMessage.value = "Error: ${t.message}"
                }
            })
        }
    }
}



