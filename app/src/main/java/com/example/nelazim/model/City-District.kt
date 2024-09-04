package com.example.nelazim.model

import android.util.Log
import com.google.gson.annotations.SerializedName
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

data class DataStrict(
    @SerializedName("il") var il: String,
    @SerializedName("plaka") val plaka: Int,
    @SerializedName("ilceleri") val ilceleri: List<String>
)
interface GetCityDistrictApi {
    @GET("data")
    suspend fun getCityDistrict(): List<DataStrict>
}

object RetrofitClient {
    private const val BASE_URL = "https://raw.githubusercontent.com/KadirDuran/Data-Json/main/"

    val apiService: GetCityDistrictApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(GetCityDistrictApi::class.java)
    }
}

suspend fun getCityDistrict(): List<DataStrict> {
    return withContext(Dispatchers.IO) {
        try {
            RetrofitClient.apiService.getCityDistrict()
        } catch (e: Exception) {
            Log.e("Error", "Error fetching data", e)
            emptyList()
        }
    }
}