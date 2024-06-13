package com.capstone.tanampintar.ui.detail

import android.content.Intent
import android.os.Bundle
import android.view.WindowManager.LayoutParams.*
import android.widget.GridView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.capstone.tanampintar.R
import com.capstone.tanampintar.adapter.Plant
import com.capstone.tanampintar.adapter.PlantAdapter
import com.capstone.tanampintar.data.network.ResultState
import com.capstone.tanampintar.data.network.response.DetailSoilResponse
import com.capstone.tanampintar.data.network.response.SoilResponse
import com.capstone.tanampintar.databinding.ActivityDetailBinding
import com.capstone.tanampintar.factory.ViewModelFactory
import com.capstone.tanampintar.ui.MainActivity
import com.capstone.tanampintar.ui.home.HomeViewModel

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    private val soilViewModel: DetailViewModel by viewModels<DetailViewModel>{
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViewModel()

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

    private fun setupViewModel() {
        val id = intent.getStringExtra(SOIL_ID)
        if (id != null) {
            soilViewModel.getDetailSoil(id)
        }
        soilViewModel.detailSoil.observe(this){
                soil ->
            when(soil){
                is ResultState.Loading -> {
                }
                is ResultState.Success -> {
                   setupView(soil.data)
                }
                is ResultState.Error -> {
                    Toast.makeText(this, soil.error, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun setupView(soil : DetailSoilResponse){
        if (soil != null) {
            binding.apply {
                textTitle.text = soil.data!!.soilName
                textDescription.text = soil.data.description
                Glide.with(this@DetailActivity)
                    .load(soil.data.imageUrl)
                    .into(imgDetail)
            }
        }
    }

    companion object {
        const val SOIL_ID = "soil_id"
    }
}