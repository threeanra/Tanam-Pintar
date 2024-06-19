package com.capstone.tanampintar.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.capstone.tanampintar.data.local.room.AnalysisResult
import com.capstone.tanampintar.databinding.HistoryListBinding

class HistoryAdapter :
    ListAdapter<AnalysisResult, HistoryAdapter.HistoryViewHolder>(AnalysisResultDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val binding = HistoryListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HistoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    inner class HistoryViewHolder(private val binding: HistoryListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: AnalysisResult) {
            binding.apply {
                titleHistory.text = item.title
                timeHistory.text = item.time
                // Implement image loading if necessary
                Glide.with(itemView.context).load(item.image).into(imagePlace)
            }
        }
    }
}

class AnalysisResultDiffCallback : DiffUtil.ItemCallback<AnalysisResult>() {
    override fun areItemsTheSame(oldItem: AnalysisResult, newItem: AnalysisResult): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: AnalysisResult, newItem: AnalysisResult): Boolean {
        return oldItem == newItem
    }
}

