package com.capstone.tanampintar.data.local.pref

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.capstone.tanampintar.data.network.retrofit.ApiConfig.removeToken
import com.capstone.tanampintar.data.network.retrofit.ApiConfig.setToken
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_preferences")

class UserPreferences private constructor(private val dataStore: DataStore<Preferences>) {

    suspend fun saveToken(token: String) {
        dataStore.edit { preferences ->
            preferences[AUTH] = token
        }
        setToken(token)
    }

    fun getToken(): Flow<String?> {
        return dataStore.data.map { preferences ->
            preferences[AUTH]
        }
    }

    suspend fun clearToken() {
        dataStore.edit { preferences ->
            preferences.remove(AUTH)
        }
        removeToken()
    }

    companion object {
        @Volatile
        private var INSTANCE: UserPreferences? = null
        private val AUTH = stringPreferencesKey("token")

        fun getInstance(dataStore: DataStore<Preferences>): UserPreferences {
            return INSTANCE ?: synchronized(this) {
                val instance = UserPreferences(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }
}