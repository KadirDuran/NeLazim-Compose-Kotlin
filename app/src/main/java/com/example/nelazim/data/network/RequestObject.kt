package com.example.nelazim.data.network

import com.example.nelazim.data.model.CurrencyApiResponse
import com.example.nelazim.data.model.DataStrict
import com.example.nelazim.data.model.PharmacyApiResponse
import com.example.nelazim.data.model.PrayerApiResponse
import com.example.nelazim.data.model.WheatherApiResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface ApiService {
    /*--------------Github API Start--------------------*/
    @GET("data")
    suspend fun getCityDistrict(): List<DataStrict>
    /*--------------Github API End----------------------*/
    /*--------------Collect API Start-------------------*/
    @GET("weather/getWeather")
    fun getWeather(
        @Query("data.lang") data_lang: String,
        @Query("data.city") data_city: String,
        @Header("authorization") authHeader: String
    ): Call<WheatherApiResponse>
    @GET("pray/all")
    fun getPrayer(
        @Query("data.city") data_city: String,
        @Header("authorization") authHeader: String
    ): Call<PrayerApiResponse>
    @GET("economy/allCurrency")
    fun getCurrency(
        @Header("authorization") authHeader: String
    ): Call<CurrencyApiResponse>
    @GET("health/dutyPharmacy")
    fun getDutyPharmacy(
        @Query("ilce") ilce: String,
        @Query("il") il: String,
        @Header("authorization") authHeader: String
    ): Call<PharmacyApiResponse>
    /*--------------Collect API End-------------------*/
}