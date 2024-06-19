package com.capstone.tanampintar.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.capstone.tanampintar.R
import com.capstone.tanampintar.data.local.room.AnalysisResult
import com.capstone.tanampintar.databinding.HistoryListBinding
import com.capstone.tanampintar.ui.history.HistoryViewModel

class HistoryAdapter(private val viewModel: HistoryViewModel) :
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
                Glide.with(itemView.context).load(item.image).into(imagePlace)
                btnDelete.setOnClickListener {
                    showDeleteDialog(item)
                }
            }
        }

        private fun showDeleteDialog(item : AnalysisResult) {
            val dialogView = LayoutInflater.from(itemView.context).inflate(R.layout.alert_dialog_history, null)

            val builder = AlertDialog.Builder(itemView.context)
                .setView(dialogView)
                .setCancelable(false)

            val alertDialog = builder.create()
            alertDialog.window?.setBackgroundDrawableResource(android.R.color.transparent) //to make alert dialog rounded corner

            dialogView.findViewById<Button>(R.id.btnYes).setOnClickListener {
                viewModel.deleteAnalysisResult(item)
                alertDialog.dismiss()
            }

            dialogView.findViewById<Button>(R.id.btnNo).setOnClickListener {
                alertDialog.dismiss()
            }

            alertDialog.show()
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

