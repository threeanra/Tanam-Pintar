package com.capstone.tanampintar.ui.shop

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.tanampintar.adapter.FertilizerAdapter
import com.capstone.tanampintar.adapter.ToolsAdapter
import com.capstone.tanampintar.data.local.model.FertilizerRecommendationList
import com.capstone.tanampintar.data.local.model.StuffRecommendationList
import com.capstone.tanampintar.databinding.ActivityShopBinding

class ShopActivity : AppCompatActivity() {
    private lateinit var binding : ActivityShopBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShopBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.imgBack.setOnClickListener{
            onBackPressedDispatcher.onBackPressed()
            finish()
        }

        toolsRecyclerView()
        fertilizerRecyclerView()

    }

    private fun toolsRecyclerView() {
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.rvTools.layoutManager = layoutManager
        val adapter = ToolsAdapter(StuffRecommendationList.tools)
        binding.rvTools.adapter = adapter
    }

    private fun fertilizerRecyclerView() {
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.rvFertilizer.layoutManager = layoutManager
        val adapter = FertilizerAdapter(FertilizerRecommendationList.fertilizer)
        binding.rvFertilizer.adapter = adapter
    }
}