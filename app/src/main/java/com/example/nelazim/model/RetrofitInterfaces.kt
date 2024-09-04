package com.example.nelazim.model

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface GetCityDistrictApi {
    @GET("data")
    suspend fun getCityDistrict(): List<DataStrict>
}
interface GetPharmacy {
    @GET("health/dutyPharmacy")
    fun getDutyPharmacy(
        @Query("ilce") ilce: String,
        @Query("il") il: String,
        @Header("authorization") authHeader: String
    ): Call<PharmacyApiResponse>
}