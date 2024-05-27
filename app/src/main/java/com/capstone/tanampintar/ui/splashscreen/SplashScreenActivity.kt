package com.capstone.tanampintar.ui.splashscreen

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.capstone.tanampintar.databinding.ActivitySplashScreenBinding
import com.capstone.tanampintar.ui.MainActivity

@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : AppCompatActivity() {

    private lateinit var binding : ActivitySplashScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Handler(Looper.getMainLooper()).postDelayed({
//            viewModel.getToken().observe(this){
//                    token ->
//                val intentActivity = Intent(this, if (token == null) LoginActivity::class.java else MainActivity::class.java)
//                startActivity(intentActivity)
//                finish()
//            }
         val intent = Intent(this, MainActivity::class.java)
         startActivity(intent)
         finish()
        },
            DELAY
        )

    }
    companion object {
        const val DELAY = 3000L
    }
}