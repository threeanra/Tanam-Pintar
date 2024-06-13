package com.capstone.tanampintar.data.network.response

import com.google.gson.annotations.SerializedName

data class PlantResponse(

	@field:SerializedName("data")
	val plant: List<Plant>,

	@field:SerializedName("status")
	val status: String
)

data class Plant(

	@field:SerializedName("soil_type")
	val soilType: String,

	@field:SerializedName("image_url")
	val imageUrl: String,

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("plant_name")
	val plantName: String
)
