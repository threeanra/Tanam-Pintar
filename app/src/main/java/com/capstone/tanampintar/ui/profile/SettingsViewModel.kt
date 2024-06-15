package com.capstone.tanampintar.ui.profile

import androidx.datastore.preferences.core.preferencesOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.capstone.tanampintar.data.local.pref.PreferencesHelper

class SettingsViewModel(private val preferencesHelper: PreferencesHelper) : ViewModel() {
    private val _darkMode = MutableLiveData<Boolean>()
    val darkMode: LiveData<Boolean> = _darkMode

    init {
        _darkMode.value = preferencesHelper.getDarkMode()
    }

    fun setDarkMode(isEnable: Boolean) {
        _darkMode.value = isEnable
    }
}