package com.capstone.tanampintar.data.di

import android.content.Context
import com.capstone.tanampintar.data.local.pref.UserPreferences
import com.capstone.tanampintar.data.local.pref.dataStore
import com.capstone.tanampintar.data.network.retrofit.ApiConfig
import com.capstone.tanampintar.repository.SoilRepository
import com.capstone.tanampintar.repository.UserRepository

object Injection {
    fun getAuthRepository(context: Context): UserRepository {
        val pref = UserPreferences.getInstance(context.dataStore)
        val apiService = ApiConfig.getApiService()
        return UserRepository.getInstance(apiService, pref)
    }

    fun getSoilRepository(context: Context): SoilRepository {
        val apiService = ApiConfig.getApiService()
        return SoilRepository.getInstance(apiService)
    }
}