package com.capstone.tanampintar.factory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.capstone.tanampintar.data.di.Injection
import com.capstone.tanampintar.data.local.pref.PreferencesHelper
import com.capstone.tanampintar.data.local.pref.UserPreferences
import com.capstone.tanampintar.data.local.pref.dataStore
import com.capstone.tanampintar.repository.PlantRepository
import com.capstone.tanampintar.repository.SoilRepository
import com.capstone.tanampintar.repository.UserRepository
import com.capstone.tanampintar.ui.detail.DetailViewModel
import com.capstone.tanampintar.ui.home.HomeViewModel
import com.capstone.tanampintar.ui.login.LoginViewModel
import com.capstone.tanampintar.ui.profile.SettingsViewModel
import com.capstone.tanampintar.ui.register.RegisterViewModel
import com.capstone.tanampintar.ui.splashscreen.AuthViewModel
import java.security.PrivateKey


class ViewModelFactory private constructor(
    private val userRepository: UserRepository,
    private val soilRepository: SoilRepository,
    private val plantRepository: PlantRepository,
    private val pref: UserPreferences,
    private val preferencesHelper: PreferencesHelper
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

            modelClass.isAssignableFrom(HomeViewModel::class.java) -> {
                HomeViewModel(soilRepository) as T
            }

            modelClass.isAssignableFrom(DetailViewModel::class.java) -> {
                DetailViewModel(soilRepository, plantRepository) as T
            }

            modelClass.isAssignableFrom(SettingsViewModel::class.java) -> {
                SettingsViewModel(preferencesHelper) as T
            }

            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null
        fun getInstance(context: Context): ViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: ViewModelFactory(
                    Injection.getAuthRepository(context),
                    Injection.getSoilRepository(context),
                    Injection.getPlantRepository(context),
                    pref = UserPreferences.getInstance(context.dataStore),
                    PreferencesHelper(context)
                )
            }.also { instance = it }
    }
}

