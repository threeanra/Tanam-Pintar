package com.capstone.tanampintar.ui.detail

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.capstone.tanampintar.adapter.PlantAdapter
import com.capstone.tanampintar.data.network.ResultState
import com.capstone.tanampintar.data.network.response.DetailSoilResponse
import com.capstone.tanampintar.data.network.response.PlantResponse
import com.capstone.tanampintar.databinding.ActivityDetailBinding
import com.capstone.tanampintar.factory.ViewModelFactory

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    private val viewModel: DetailViewModel by viewModels<DetailViewModel>{
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
    }

    private fun setupViewModel() {
        val id = intent.getStringExtra(SOIL_ID)
        if (id != null) {
            viewModel.getDetailSoil(id)
        }

        viewModel.detailSoil.observe(this){
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

        viewModel.plant.observe(this){
                plant ->
            when(plant){
                is ResultState.Loading -> {
                }
                is ResultState.Success -> {
                    setupRecyclerView(plant.data, id!!)
                }
                is ResultState.Error -> {
                    Toast.makeText(this, plant.error, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun setupView(soil : DetailSoilResponse){
        binding.apply {
            textTitle.text = soil.data!!.soilName
            textDescription.text = soil.data.description
            Glide.with(this@DetailActivity)
                .load(soil.data.imageUrl)
                .into(imgDetail)
        }
    }

    private fun setupRecyclerView(plantResponse: PlantResponse, soilId: String) {
        // Find the Plant object with the specified ID
        val matchingPlants = plantResponse.plant.filter { it.soilType == soilId }
        if (matchingPlants.isNotEmpty()) {
            binding.plantList.layoutManager =  LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
            val adapter = PlantAdapter(matchingPlants)
            binding.plantList.adapter = adapter
        }
    }

    companion object {
        const val SOIL_ID = "soil_id"
    }
}