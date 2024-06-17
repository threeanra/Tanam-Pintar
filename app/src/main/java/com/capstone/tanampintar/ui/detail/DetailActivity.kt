package com.capstone.tanampintar.ui.detail

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
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
import com.facebook.shimmer.ShimmerFrameLayout

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    private val viewModel: DetailViewModel by viewModels<DetailViewModel> {
        ViewModelFactory.getInstance(this)
    }

    private lateinit var shimmerFrameLayout: ShimmerFrameLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        shimmerFrameLayout = binding.shimmerPlantContainer

        Handler(Looper.getMainLooper()).postDelayed({
            setupViewModel()
        }, 3000)



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
        viewModel.detailSoil.observe(this) { soil ->
            when (soil) {
                is ResultState.Loading -> {
                    shimmerFrameLayout.startShimmer()
                    shimmerFrameLayout.visibility = View.VISIBLE

                }

                is ResultState.Success -> {
                    shimmerFrameLayout.stopShimmer()
                    shimmerFrameLayout.visibility = View.GONE
                    setupView(soil.data)
                }

                is ResultState.Error -> {
                    shimmerFrameLayout.stopShimmer()
                    shimmerFrameLayout.visibility = View.GONE
                    Toast.makeText(this, soil.error, Toast.LENGTH_SHORT).show()
                }
            }
        }

        viewModel.plant.observe(this) { plant ->
            when (plant) {
                is ResultState.Loading -> {
                    shimmerFrameLayout.startShimmer()
                    shimmerFrameLayout.visibility = View.VISIBLE
                }

                is ResultState.Success -> {
                    shimmerFrameLayout.stopShimmer()
                    shimmerFrameLayout.visibility = View.GONE
                    setupRecyclerView(plant.data, id!!)
                }

                is ResultState.Error -> {
                    shimmerFrameLayout.stopShimmer()
                    shimmerFrameLayout.visibility = View.GONE
                    Toast.makeText(this, plant.error, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun setupView(soil: DetailSoilResponse) {
        binding.apply {
            shimmerPlantContainer.stopShimmer()
            shimmerPlantContainer.visibility = View.GONE
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
            binding.plantList.layoutManager =
                LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
            val adapter = PlantAdapter(matchingPlants)
            binding.plantList.adapter = adapter
        }
    }

    companion object {
        const val SOIL_ID = "soil_id"
    }
}