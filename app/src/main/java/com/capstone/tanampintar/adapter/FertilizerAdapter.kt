package com.capstone.tanampintar.adapter

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.capstone.tanampintar.data.local.model.FertilizerRecommendationModel
import com.capstone.tanampintar.databinding.ShopListBinding

class FertilizerAdapter(
    private val fertilizer: List<FertilizerRecommendationModel>
) : RecyclerView.Adapter<FertilizerAdapter.MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ShopListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int = fertilizer.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val fertilizers = fertilizer[position]
        holder.bind(fertilizers)
    }

    class MyViewHolder(private val binding: ShopListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(fertilizer: FertilizerRecommendationModel) {
            binding.stuffTitle.text = fertilizer.name
            Glide.with(binding.stuffImage.context)
                .load(fertilizer.image)
                .into(binding.stuffImage)

            binding.root.setOnClickListener {
                val intentUrl = Intent(Intent.ACTION_VIEW, Uri.parse(fertilizer.url))
                binding.root.context.startActivity(intentUrl)
            }
        }
    }
}
