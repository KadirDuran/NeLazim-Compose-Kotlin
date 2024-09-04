package com.example.nelazim.model

import android.content.Context
import com.example.nelazim.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query


data class PharmacyApiResponse(
    val success: Boolean,
    val result: List<Pharmacy>
)

data class Pharmacy(
    val name: String,
    val dist: String,
    val address: String,
    val phone: String,
    val loc: String
)
interface GetPharmacy {
    @GET("health/dutyPharmacy")
    fun getDutyPharmacy(
        @Query("ilce") ilce: String,
        @Query("il") il: String,
        @Header("authorization") authHeader: String
    ): Call<PharmacyApiResponse>
}
object RetrofitClientPharmacy {
    private const val BASE_URL = "https://api.collectapi.com/"

    val instance: GetPharmacy by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofit.create(GetPharmacy::class.java)
    }
}
fun getPharmacys(context: Context, district : String, city : String, callback: (ArrayList<Pharmacy>) -> Unit){
    val apiService = RetrofitClientPharmacy.instance
    val call = apiService.getDutyPharmacy(district, city,context.getString(R.string.API_KEY))
    val pharmacyList = arrayListOf<Pharmacy>()

    call.enqueue(object : Callback<PharmacyApiResponse> {

        override fun onResponse(call: Call<PharmacyApiResponse>, response: Response<PharmacyApiResponse>) {

            if (response.isSuccessful) {
                val apiResponse = response.body()
                apiResponse?.result?.forEach { pharmacy ->
                    pharmacyList.add(pharmacy)//liste donecek
                }
                callback(pharmacyList)
            } else {
                println("Response Error Body: ${response.errorBody()?.string()}")
                callback(arrayListOf())//liste boş donecek
            }
        }

        override fun onFailure(call: Call<PharmacyApiResponse>, t: Throwable) {
            println("Network Error: ${t.message}")
            callback(arrayListOf()) //liste boş donecek
        }
    })

}

