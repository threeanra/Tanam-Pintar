package com.capstone.tanampintar.factory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.capstone.tanampintar.data.di.Injection
import com.capstone.tanampintar.data.local.pref.UserPreferences
import com.capstone.tanampintar.data.local.pref.dataStore
import com.capstone.tanampintar.repository.UserRepository
import com.capstone.tanampintar.ui.login.LoginViewModel
import com.capstone.tanampintar.ui.register.RegisterViewModel
import com.capstone.tanampintar.ui.splashscreen.AuthViewModel


class ViewModelFactory private constructor(
    private val userRepository: UserRepository,
    private val pref: UserPreferences
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        return when {
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                LoginViewModel(userRepository) as T
            }
            modelClass.isAssignableFrom(RegisterViewModel::class.java) -> {
                RegisterViewModel(userRepository) as T
            }
            modelClass.isAssignableFrom(AuthViewModel::class.java) -> {
                AuthViewModel(pref) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null
        fun getInstance(context: Context): ViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: ViewModelFactory(Injection.provideRepository(context), pref = UserPreferences.getInstance(context.dataStore))
            }.also { instance = it }
    }
}
