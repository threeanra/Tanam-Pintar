package com.capstone.tanampintar.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.capstone.tanampintar.data.network.ResultState
import com.capstone.tanampintar.data.network.response.PlantResponse
import com.capstone.tanampintar.data.network.retrofit.ApiService

class PlantRepository(
    private val apiService: ApiService
) {
    private val _plant = MutableLiveData<ResultState<PlantResponse>>()
    val plant: LiveData<ResultState<PlantResponse>> = _plant

    suspend fun getPlant() {
        _plant.value = ResultState.Loading
        try {
            val response = apiService.getPlant()
            if (response.plant != null) {
                _plant.value = ResultState.Success(response)
            } else {
                _plant.value = ResultState.Error("Error getting soil data")
            }
        } catch (e: Exception) {
            // Handle other exceptions
            _plant.value = ResultState.Error("An unexpected error occurred")
            // Log the exception for debugging
            Log.e("SoilRepository", "Exception", e)
        }
    }

    companion object {
        @Volatile
        private var instance: PlantRepository? = null
        fun getInstance(apiService: ApiService): PlantRepository =
            instance ?: synchronized(this) {
                instance ?: PlantRepository(apiService).also { instance = it }
            }
    }
}