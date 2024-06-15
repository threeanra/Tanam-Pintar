package com.capstone.tanampintar.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.capstone.tanampintar.data.network.response.Plant
import com.capstone.tanampintar.databinding.PlantRecommendationsBinding


class PlantAdapter(
    private val plant: List<Plant>
) : RecyclerView.Adapter<PlantAdapter.MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = PlantRecommendationsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int = plant.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val plant = plant[position]
        holder.bind(plant)
    }

    class MyViewHolder(private val binding: PlantRecommendationsBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(plant: Plant){
            binding.plantTitle.text = plant.plantName
            Glide.with(binding.plantImage.context)
                .load(plant.imageUrl)
                .into(binding.plantImage)
        }
    }
}