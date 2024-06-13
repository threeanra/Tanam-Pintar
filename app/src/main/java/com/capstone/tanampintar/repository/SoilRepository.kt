package com.capstone.tanampintar.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.capstone.tanampintar.data.local.pref.UserPreferences
import com.capstone.tanampintar.data.network.ResultState
import com.capstone.tanampintar.data.network.response.DetailSoilResponse
import com.capstone.tanampintar.data.network.response.LoginResponse
import com.capstone.tanampintar.data.network.response.Soil
import com.capstone.tanampintar.data.network.response.SoilResponse
import com.capstone.tanampintar.data.network.retrofit.ApiService

class SoilRepository(
    private val apiService: ApiService
) {
    private val _soil = MutableLiveData<ResultState<SoilResponse>>()
    val soil: LiveData<ResultState<SoilResponse>> = _soil

    private val _detailSoil = MutableLiveData<ResultState<DetailSoilResponse>>()
    val detailSoil: LiveData<ResultState<DetailSoilResponse>> = _detailSoil

    suspend fun getSoil() {
        _soil.value = ResultState.Loading
        try {
            val response = apiService.getSoil()
            if (response.data != null) {
                _soil.value = ResultState.Success(response)
            } else {
                _soil.value = ResultState.Error("Error getting soil data")
            }
        } catch (e: Exception) {
            // Handle other exceptions
            _soil.value = ResultState.Error("An unexpected error occurred")
            // Log the exception for debugging
            Log.e("SoilRepository", "Exception during login", e)
        }
    }

    suspend fun getDetailSoil(id: String) {
        _detailSoil.value = ResultState.Loading
        try {
            val response = apiService.getDetailSoil(id)
            if (response.data != null) {
                _detailSoil.value = ResultState.Success(response)
            } else {
                _detailSoil.value = ResultState.Error("Error getting soil data")
            }
        } catch (e: Exception) {
            // Handle other exceptions
            _detailSoil.value = ResultState.Error("Error getting soil data")
            // Log the exception for debugging
            Log.e("SoilRepository", "Exception during login", e)
        }
    }

    companion object {
        @Volatile
        private var instance: SoilRepository? = null
        fun getInstance(apiService: ApiService): SoilRepository =
            instance ?: synchronized(this) {
                instance ?: SoilRepository(apiService).also { instance = it }
            }
    }
}