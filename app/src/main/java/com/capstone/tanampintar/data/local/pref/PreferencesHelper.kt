package com.capstone.tanampintar.data.local.pref

import android.content.Context
import android.content.SharedPreferences

class PreferencesHelper(context: Context) {
    private val preferences: SharedPreferences =
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    companion object {
        private const val PREF_NAME = "preferences"
        private const val KEY_DARK_MODE = "dark_mode"

        @Volatile
        private var instance: PreferencesHelper? = null

        fun getInstance(context: Context): PreferencesHelper =
            instance ?: synchronized(this) {
                instance ?: PreferencesHelper(context)
            }.also { instance = it }
    }

    fun setDarkMode(isEnabled: Boolean) {
        preferences.edit().putBoolean(KEY_DARK_MODE, isEnabled).apply()
    }

    fun getDarkMode(): Boolean {
        return preferences.getBoolean(KEY_DARK_MODE, false)
    }

}