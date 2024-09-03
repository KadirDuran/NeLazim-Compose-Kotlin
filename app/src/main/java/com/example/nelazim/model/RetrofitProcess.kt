package com.example.nelazim.model

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET


interface GetCityDistrictApi {
    @GET("data")
    suspend fun getCityDistrict(): List<DataStrict>
}

object RetrofitClient {
    private const val BASE_URL = "https://raw.githubusercontent.com/KadirDuran/Il-Ilce-Json/main/"

    val apiService: GetCityDistrictApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(GetCityDistrictApi::class.java)
    }
}
suspend fun getData(): List<DataStrict> {
    return withContext(Dispatchers.IO) {
        try {
            RetrofitClient.apiService.getCityDistrict()
        } catch (e: Exception) {
            Log.e("Error", "Error fetching data", e)
            emptyList()
        }
    }
}
