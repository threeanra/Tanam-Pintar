package com.capstone.tanampintar.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.capstone.tanampintar.data.network.response.Soil
import com.capstone.tanampintar.databinding.SoilTypeListBinding
import com.capstone.tanampintar.ui.detail.DetailActivity

class SoilAdapter(
    private val soil: List<Soil>
) : RecyclerView.Adapter<SoilAdapter.MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding =
            SoilTypeListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int = soil.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val soil = soil.get(position)
        holder.bind(soil)
    }

    class MyViewHolder(private val binding: SoilTypeListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(soil: Soil) {
            binding.soilTypeTitle.text = soil.soilName
            Glide.with(binding.soilImage.context)
                .load(soil.imageUrl)
                .into(binding.soilImage)

            binding.root.setOnClickListener {
                val intentDetailActivity = Intent(binding.root.context, DetailActivity::class.java)
                intentDetailActivity.putExtra(DetailActivity.SOIL_ID, soil.id)
                binding.root.context.startActivity(intentDetailActivity)
            }

        }
    }
}