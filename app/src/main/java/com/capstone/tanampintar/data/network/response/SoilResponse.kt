package com.capstone.tanampintar.data.network.response

import com.google.gson.annotations.SerializedName

data class SoilResponse(

	@field:SerializedName("data")
	val data: List<Soil>,

	@field:SerializedName("status")
	val status: String
)

data class Soil(

	@field:SerializedName("soil_name")
	val soilName: String,

	@field:SerializedName("image_url")
	val imageUrl: String,

	@field:SerializedName("description")
	val description: String,

	@field:SerializedName("id")
	val id: String
)
