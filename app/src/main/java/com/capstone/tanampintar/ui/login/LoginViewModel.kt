package com.capstone.tanampintar.ui.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.tanampintar.data.local.model.User
import com.capstone.tanampintar.data.network.ResultState
import com.capstone.tanampintar.data.network.response.LoginResponse
import com.capstone.tanampintar.repository.UserRepository
import kotlinx.coroutines.launch
import retrofit2.HttpException

class LoginViewModel(
    private val userRepository: UserRepository,
): ViewModel(){

    private val _responseResult = MutableLiveData<ResultState<LoginResponse>>()
    val responseResult: LiveData<ResultState<LoginResponse>> = _responseResult


    fun submitLogin(email: String, password: String) {
        viewModelScope.launch {
            _responseResult.value = ResultState.Loading
            try {
                val response = userRepository.login(email, password)
                if (response.data?.token?.isNotEmpty() == true) {
                    userRepository.saveUser(
                        User(
                            response.data.id,
                            response.data.name,
                            email,
                            response.data.token
                        )
                    )
                    _responseResult.value = ResultState.Success(response)
                } else {
                    _responseResult.value = ResultState.Error(response.message.orEmpty())
                }
            } catch (e: HttpException) {
                handleHttpException(e)
            } catch (e: Exception) {
                // Handle other exceptions
                _responseResult.value = ResultState.Error("An unexpected error occurred")
                // Log the exception for debugging
                Log.e("LoginViewModel", "Exception during login", e)
            }
        }
    }


    private fun handleHttpException(e: HttpException) {
        val errorBody = e.response()?.errorBody()?.string()
        val errorMessage = errorBody ?: e.message() ?: "Unknown error"
        _responseResult.value = ResultState.Error(errorMessage)
        // Log the error for debugging
        Log.e("LoginViewModel", "HTTP Exception during login", e)
    }
}