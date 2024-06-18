package com.capstone.tanampintar.ui.splashscreen

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.WindowInsets
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.capstone.tanampintar.data.local.pref.PreferencesHelper
import com.capstone.tanampintar.databinding.ActivitySplashScreenBinding
import com.capstone.tanampintar.factory.ViewModelFactory
import com.capstone.tanampintar.ui.MainActivity
import com.capstone.tanampintar.ui.login.LoginActivity

@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : AppCompatActivity() {

    private val viewModel:AuthViewModel by viewModels<AuthViewModel>{
        ViewModelFactory.getInstance(this)
    }

    private lateinit var binding: ActivitySplashScreenBinding
    private lateinit var preferencesHelper: PreferencesHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        preferencesHelper = PreferencesHelper.getInstance(this)

        if (preferencesHelper.getDarkMode()) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }

        Handler(Looper.getMainLooper()).postDelayed(
            {
                viewModel.getUser().observe(this) { user ->
                    if (user != null && user.token.isNotEmpty()) {
                        MainActivity.start(this)
                        finish()
                    } else {
                        LoginActivity.start(this)
                        finish()
                    }
                }
            },
            DELAY
        )

        //Full screen mode
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            @Suppress("DEPRECATION")
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }

    }

    companion object {
        const val DELAY = 3000L
    }
}