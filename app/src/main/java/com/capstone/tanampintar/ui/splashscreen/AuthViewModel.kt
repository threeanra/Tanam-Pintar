package com.capstone.tanampintar.ui.splashscreen

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.capstone.tanampintar.data.local.pref.UserPreferences
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AuthViewModel(
    private val pref: UserPreferences
): ViewModel(){

    fun getToken() = pref.getToken().asLiveData()

    fun logout(){
        viewModelScope.launch {
            pref.clearToken()
        }
    }
}