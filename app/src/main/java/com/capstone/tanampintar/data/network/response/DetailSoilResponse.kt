package com.capstone.tanampintar.data.network.response

import com.google.gson.annotations.SerializedName

data class DetailSoilResponse(

    @field:SerializedName("data")
    val data: Soil? = null,

    @field:SerializedName("status")
    val status: String
)