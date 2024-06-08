package com.capstone.tanampintar.data.network.retrofit

import com.capstone.tanampintar.BuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiConfig {

    private var token = ""

    fun setToken(value: String) {
        token = value
    }

    fun removeToken() {
        token = ""
    }

    private fun authInterceptor(): Interceptor {
        return Interceptor { chain ->
            val req = chain.request()
            val finalToken = token
            val requestHeaders = req.newBuilder()
                .addHeader("Authorization", "Bearer $finalToken")
                .build()
            chain.proceed(requestHeaders)
        }
    }

    private fun loggingInterceptor(): HttpLoggingInterceptor {
        return if(BuildConfig.DEBUG) {
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        } else {
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.NONE)
        }
    }

    fun getApiService(): ApiService {
        val client = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor())
            .addInterceptor(authInterceptor())
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()

        return retrofit.create(ApiService::class.java)
    }
}