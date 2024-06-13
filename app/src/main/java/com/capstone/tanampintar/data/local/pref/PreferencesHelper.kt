package com.capstone.tanampintar.data.local.pref

import android.content.Context
import android.content.SharedPreferences

class PreferencesHelper(context: Context) {
    companion object {
        private val PREFS_NAME = "settings_pref"
        private val DARK_MODE_KEY = "dark_mode"
    }

    private val sharedPreference: SharedPreferences =
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun setDarkMode(isEnable: Boolean) {
        sharedPreference.edit().putBoolean(DARK_MODE_KEY, isEnable).apply()
    }

    fun getDarkMode(): Boolean {
        return sharedPreference.getBoolean(DARK_MODE_KEY, false)
    }

}