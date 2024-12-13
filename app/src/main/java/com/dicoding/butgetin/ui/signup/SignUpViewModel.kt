package com.dicoding.butgetin.ui.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.butgetin.data.model.RegisterRequest
import com.dicoding.butgetin.data.model.RegisterResponse
import com.dicoding.butgetin.data.repository.UserRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignUpViewModel(private val userRepository: UserRepository) : ViewModel() {

    private val _signUpResult = MutableLiveData<Boolean>()
    val signUpResult: LiveData<Boolean> get() = _signUpResult

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

    fun registerUser(request: RegisterRequest) {
        userRepository.registerUser(request).enqueue(object : Callback<RegisterResponse> {
            override fun onResponse(call: Call<RegisterResponse>, response: Response<RegisterResponse>) {
                if (response.isSuccessful) {
                    _signUpResult.value = true
                } else {
                    _signUpResult.value = false
                    _errorMessage.value = "Sign up failed: ${response.errorBody()?.string()}"
                }
            }

            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                _signUpResult.value = false
                _errorMessage.value = "Error: ${t.message}"
            }
        })
    }
}