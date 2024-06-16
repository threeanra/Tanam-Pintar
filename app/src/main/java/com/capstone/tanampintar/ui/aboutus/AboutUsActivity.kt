package com.capstone.tanampintar.ui.aboutus

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.capstone.tanampintar.R
import com.capstone.tanampintar.databinding.ActivityAboutUsBinding

class AboutUsActivity : AppCompatActivity() {

    private lateinit var binding : ActivityAboutUsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAboutUsBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}