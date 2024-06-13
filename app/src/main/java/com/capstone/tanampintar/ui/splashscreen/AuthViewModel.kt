package com.capstone.tanampintar.ui.splashscreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.capstone.tanampintar.data.local.model.User
import com.capstone.tanampintar.data.local.pref.UserPreferences
import kotlinx.coroutines.launch

class AuthViewModel(
    private val pref: UserPreferences
): ViewModel(){

    fun getUser(): LiveData<User> = pref.getUser().asLiveData()

    fun logout(){
        viewModelScope.launch {
            pref.clearToken()
        }
    }
}