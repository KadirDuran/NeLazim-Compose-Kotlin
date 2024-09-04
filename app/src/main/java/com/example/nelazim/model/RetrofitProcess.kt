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
import kotlin.math.log


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
object RetrofitClient2 {
    private const val BASE_URL = "https://api.collectapi.com/"

    val instance: GetPharmacy by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofit.create(GetPharmacy::class.java)
    }
}
fun getPharmacys(district : String,city : String) {
    val apiService = RetrofitClient2.instance
    val authHeader = "apikey **************"
    val call = apiService.getDutyPharmacy(district, city, authHeader)

    call.enqueue(object : Callback<PharmacyApiResponse> {
        override fun onResponse(call: Call<PharmacyApiResponse>, response: Response<PharmacyApiResponse>) {
            if (response.isSuccessful) {
                val apiResponse = response.body()
                apiResponse?.result?.forEach { pharmacy ->
                    println("Name: ${pharmacy.name}")
                    println("District: ${pharmacy.dist}")
                    println("Address: ${pharmacy.address}")
                    println("Phone: ${pharmacy.phone}")
                    println("Location: ${pharmacy.loc}")
                }
            } else {
                println("Response Error Code: ${response.code()}")
                println("Response Error Body: ${response.errorBody()?.string()}")
            }
        }

        override fun onFailure(call: Call<PharmacyApiResponse>, t: Throwable) {
            println("Network Error: ${t.message}")
        }
    })
}
