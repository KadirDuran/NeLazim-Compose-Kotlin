package com.example.nelazim.data.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object GithubApi {
    private const val BASE_URL = "https://raw.githubusercontent.com/KadirDuran/Data-Json/main/"
    val apiService: ApiService by lazy {
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}