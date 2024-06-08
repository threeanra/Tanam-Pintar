package com.capstone.tanampintar.ui.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.tanampintar.data.network.ResultState
import com.capstone.tanampintar.data.network.response.RegisterResponse
import com.capstone.tanampintar.repository.UserRepository
import kotlinx.coroutines.launch
import retrofit2.HttpException

class RegisterViewModel(
    private val userRepository: UserRepository
): ViewModel() {
    private val _responseResult = MutableLiveData<ResultState<RegisterResponse>>()
    val responseResult: LiveData<ResultState<RegisterResponse>> = _responseResult

    fun submitRegister(name:String,email:String, password:String){
        viewModelScope.launch {
            try {
                _responseResult.value = ResultState.Loading
                val response = userRepository.register(name,email,password)
                if (!response.status.isNullOrEmpty()) {
                    _responseResult.value = ResultState.Success(response)
                }
            }catch (e: HttpException){
                val errorBody = e.response()?.errorBody()?.string()
                _responseResult.value = ResultState.Error(errorBody?:e.message())
            }
        }
    }
}