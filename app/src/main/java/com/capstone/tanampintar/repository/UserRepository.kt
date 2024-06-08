package com.capstone.tanampintar.repository

import com.capstone.tanampintar.data.local.pref.UserPreferences
import com.capstone.tanampintar.data.network.retrofit.ApiService

class UserRepository private constructor(
    private val apiService: ApiService,
    private val pref: UserPreferences,
) {
    fun getToken() = pref.getToken()
    suspend fun saveToken(token: String) = pref.saveToken(token)
    companion object {
        @Volatile
        private var instance: UserRepository? = null
        fun getInstance(apiService: ApiService, pref: UserPreferences): UserRepository =
            instance ?: synchronized(this) {
                instance ?: UserRepository(apiService, pref).also { instance = it }
            }
    }
}