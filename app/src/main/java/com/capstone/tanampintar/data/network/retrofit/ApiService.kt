package com.capstone.tanampintar.data.network.retrofit

import com.capstone.tanampintar.data.network.response.DetailSoilResponse
import com.capstone.tanampintar.data.network.response.DetectionResponse
import com.capstone.tanampintar.data.network.response.LoginResponse
import com.capstone.tanampintar.data.network.response.PlantResponse
import com.capstone.tanampintar.data.network.response.RegisterResponse
import com.capstone.tanampintar.data.network.response.Soil
import com.capstone.tanampintar.data.network.response.SoilResponse
import okhttp3.MultipartBody
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

interface ApiService {

    @FormUrlEncoded
    @POST("users/register")
    suspend fun register(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String
    ): RegisterResponse

    @FormUrlEncoded
    @POST("users/login")
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): LoginResponse

    @GET("soils")
    suspend fun getSoil(): SoilResponse

    @GET("soils/{id}")
    suspend fun getDetailSoil(
        @Path("id") id: String
    ): DetailSoilResponse

    @GET("plants")
    suspend fun getPlant(): PlantResponse

    @Multipart
    @POST("predict")
    suspend fun uploadImage(
        @Part image: MultipartBody.Part
    ): DetectionResponse

}