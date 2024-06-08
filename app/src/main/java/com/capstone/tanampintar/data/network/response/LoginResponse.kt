package com.capstone.tanampintar.data.network.response

import com.google.gson.annotations.SerializedName

data class LoginResponse(

	@field:SerializedName("data")
	val data: Data? = null,

	@field:SerializedName("status")
	val status: String,

	@field:SerializedName("message")
	val message: String? = null
)


