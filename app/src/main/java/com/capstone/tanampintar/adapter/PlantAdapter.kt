package com.capstone.tanampintar.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.recyclerview.widget.RecyclerView
import com.capstone.tanampintar.R


data class Plant(val name: String, val imageResId: Int)

class PlantAdapter(private val plantList: List<Plant>) :

    RecyclerView.Adapter<PlantAdapter.PlantViewHolder>() {
    class PlantViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val plantImage = view.findViewById<ImageView>(R.id.plantImage)
        val plantName = view.findViewById<TextView>(R.id.plantTitle)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlantViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.plant_recommendations, parent, false)
        return PlantViewHolder(view)
    }

    override fun getItemCount() = plantList.size

    override fun onBindViewHolder(holder: PlantViewHolder, position: Int) {
        val plant = plantList[position]
        holder.plantImage.setImageResource(plant.imageResId)
        holder.plantName.text = plant.name
    }
}