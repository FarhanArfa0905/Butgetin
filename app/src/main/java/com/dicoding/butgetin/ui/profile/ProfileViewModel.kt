package com.dicoding.butgetin.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.butgetin.data.model.LogoutRequest
import com.dicoding.butgetin.data.model.LogoutResponse
import com.dicoding.butgetin.data.repository.UserRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileViewModel(private val userRepository: UserRepository) : ViewModel() {

    private val _logoutResponse = MutableLiveData<LogoutResponse?>()
    val logoutResponse: LiveData<LogoutResponse?> get() = _logoutResponse

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> get() = _error

    fun logout(email: String, password: String) {
        val logoutRequest = LogoutRequest(email, password)
        userRepository.logoutUser(logoutRequest).enqueue(object : Callback<LogoutResponse> {
            override fun onResponse(call: Call<LogoutResponse>, response: Response<LogoutResponse>) {
                if (response.isSuccessful) {
                    _logoutResponse.postValue(response.body())
                } else {
                    _error.postValue("Logout failed: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<LogoutResponse>, t: Throwable) {
                _error.postValue("Network error: ${t.message}")
            }
        })
    }
}