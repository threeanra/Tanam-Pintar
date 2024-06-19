package com.capstone.tanampintar.ui.history

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.tanampintar.adapter.HistoryAdapter
import com.capstone.tanampintar.databinding.ActivityHistoryBinding
import com.capstone.tanampintar.factory.ViewModelFactory
import com.facebook.shimmer.ShimmerFrameLayout

class HistoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHistoryBinding
    private lateinit var historyAdapter: HistoryAdapter
    private val viewModel: HistoryViewModel by viewModels {
        ViewModelFactory.getInstance(this)
    }
    private lateinit var shimmerHistory: ShimmerFrameLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        shimmerHistory = binding.shimmerHistory

        shimmerHistory.startShimmer()
        shimmerHistory.isVisible = true

        Handler(Looper.getMainLooper()).postDelayed({
            shimmerHistory.stopShimmer()
            shimmerHistory.isVisible = false
            setupRecyclerView()
            setupViewModel()
        }, 1000)


        binding.imgBack.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
            finish()
        }

    }

    private fun setupViewModel() {
        viewModel.historyList.observe(this) { historyList ->
            historyAdapter.submitList(historyList)
            binding.rvEmptyHistory.isVisible = historyList.isEmpty()
        }
    }

    private fun setupRecyclerView() {
        historyAdapter = HistoryAdapter(viewModel)
        binding.rvHistory.apply {
            adapter = historyAdapter
            layoutManager = LinearLayoutManager(this@HistoryActivity)
        }
    }

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, HistoryActivity::class.java)
            context.startActivity(intent)
        }
    }
}