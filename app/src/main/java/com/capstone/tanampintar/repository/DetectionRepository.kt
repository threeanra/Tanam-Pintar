package com.capstone.tanampintar.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.capstone.tanampintar.data.network.ResultState
import com.capstone.tanampintar.data.network.response.DetectionResponse
import com.capstone.tanampintar.data.network.retrofit.ApiService
import okhttp3.MultipartBody

class DetectionRepository(
    private val apiService: ApiService
) {
    private val _result = MutableLiveData<ResultState<DetectionResponse>>()
    val result: LiveData<ResultState<DetectionResponse>> = _result

    suspend fun uploadImage(image: MultipartBody.Part) {
        _result.value = ResultState.Loading
        try {
            val response = apiService.uploadImage(image)
            if (response.prediction != null) {
                _result.value = ResultState.Success(response)
            } else {
                _result.value = ResultState.Error("Error Analyzing")
            }
        } catch (e: Exception) {
            // Handle other exceptions
            _result.value = ResultState.Error("An unexpected error occurred")
            // Log the exception for debugging
            Log.e("Detection Repository", "Exception during Analyze", e)
        }
    }

    companion object {
        @Volatile
        private var instance: DetectionRepository? = null
        fun getInstance(apiService: ApiService): DetectionRepository =
            instance ?: synchronized(this) {
                instance ?: DetectionRepository(apiService).also { instance = it }
            }
    }
}