package com.example.nelazim.data.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object CollectApi {
    private const val BASE_URL = "https://api.collectapi.com/"

    val instance: ApiService by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofit.create(ApiService::class.java)
    }
}
