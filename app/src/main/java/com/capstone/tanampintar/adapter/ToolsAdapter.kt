package com.capstone.tanampintar.adapter

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.capstone.tanampintar.data.local.model.StuffRecommendationModel
import com.capstone.tanampintar.databinding.ShopListBinding

class ToolsAdapter(
    private val tool: List<StuffRecommendationModel>
) : RecyclerView.Adapter<ToolsAdapter.MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ShopListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int = tool.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val tools = tool[position]
        holder.bind(tools)
    }

    class MyViewHolder(private val binding: ShopListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(tool: StuffRecommendationModel) {
            binding.stuffTitle.text = tool.name
            Glide.with(binding.stuffImage.context)
                .load(tool.image)
                .into(binding.stuffImage)

            binding.root.setOnClickListener {
                val intentUrl = Intent(Intent.ACTION_VIEW, Uri.parse(tool.url))
                binding.root.context.startActivity(intentUrl)
            }
        }
    }
}
