package com.capstone.tanampintar.data.network.response

import com.google.gson.annotations.SerializedName

data class DetectionResponse(

	@field:SerializedName("prediction")
	val prediction: Int? = null
)
