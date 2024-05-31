package com.capstone.tanampintar.ui.history

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.capstone.tanampintar.R
import com.capstone.tanampintar.databinding.ActivityHistoryBinding
import com.capstone.tanampintar.databinding.ActivityMainBinding
import com.capstone.tanampintar.ui.MainActivity
import com.capstone.tanampintar.ui.login.LoginActivity

class HistoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHistoryBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tombol.setOnClickListener {
//            onBackPressedDispatcher.onBackPressed()
//            finish()
            val intent = Intent(this@HistoryActivity, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}