package com.capstone.tanampintar.ui.analyze

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.capstone.tanampintar.databinding.ActivityAnalyzeBinding

class AnalyzeActivity : AppCompatActivity() {

    private lateinit var binding : ActivityAnalyzeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAnalyzeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.imgBack.setOnClickListener{
            onBackPressedDispatcher.onBackPressed()
            finish()
        }
    }
}