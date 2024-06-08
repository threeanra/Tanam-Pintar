package com.capstone.tanampintar.data.network.response

import com.google.gson.annotations.SerializedName

data class RegisterResponse(

	@field:SerializedName("data")
	val data: Data? = null,

	@field:SerializedName("status")
	val status: String,

	@field:SerializedName("message")
	val message: String
)

data class Data(

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("token")
	val token: String
)
