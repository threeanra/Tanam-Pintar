package com.capstone.tanampintar.ui.detail

import android.content.Intent
import android.os.Bundle
import android.view.WindowManager.LayoutParams.*
import android.widget.GridView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.tanampintar.R
import com.capstone.tanampintar.adapter.Plant
import com.capstone.tanampintar.adapter.PlantAdapter
import com.capstone.tanampintar.databinding.ActivityDetailBinding
import com.capstone.tanampintar.ui.MainActivity

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.imgBack.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
            finish()
        }

        binding.plantList.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        val plantList = listOf(
            Plant("Anggur", R.drawable.image_detail),
            Plant("Tomat", R.drawable.image_preview),
            Plant("Anggur", R.drawable.image_preview),
            Plant("Tomat", R.drawable.image_preview),
            Plant("Anggur", R.drawable.image_preview),
            Plant("Tomat", R.drawable.image_preview),
            Plant("Anggur", R.drawable.image_preview),
            Plant("Tomat", R.drawable.image_preview),
        )

        val adapter = PlantAdapter(plantList)
        binding.plantList.adapter = adapter
    }
}