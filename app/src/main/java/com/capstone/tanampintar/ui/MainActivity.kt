package com.capstone.tanampintar.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.capstone.tanampintar.R
import com.capstone.tanampintar.databinding.ActivityMainBinding
import com.capstone.tanampintar.ui.analyze.AnalyzeActivity

class  MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bottomNavigation.apply {
            background = null
            menu.getItem(1).isEnabled = false
        }

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainer) as NavHostFragment
        val navController = navHostFragment.navController
        binding.bottomNavigation.setupWithNavController(navController)

        binding.fab.setOnClickListener {
            val intent = Intent(this, AnalyzeActivity::class.java)
            startActivity(intent)
        }

    }
}