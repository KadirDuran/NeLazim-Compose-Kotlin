package com.example.nelazim.model

import android.content.Context
import com.example.nelazim.R
import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query


data class PrayerApiResponse(
    @SerializedName("success" ) var success : Boolean?          = null,
    @SerializedName("result"  ) var result  : ArrayList<Prayer> = arrayListOf()
)

data class Prayer(
    @SerializedName("saat"  ) var saat  : String? = null,
    @SerializedName("vakit" ) var vakit : String? = null
)
interface GetPrayer {
    @GET("pray/all")
    fun getPrayer(
        @Query("data.city") data_city: String,
        @Header("authorization") authHeader: String
    ): Call<PrayerApiResponse>
}
object RetrofitClientPrayer {
    private const val BASE_URL = "https://api.collectapi.com/"

    val instance: GetPrayer by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofit.create(GetPrayer::class.java)
    }
}
fun getPrayers(context: Context, city : String, callback: (ArrayList<Prayer>) -> Unit){
    val apiService = RetrofitClientPrayer.instance
    val call = apiService.getPrayer(city,context.getString(R.string.API_KEY))
    val prayerList = arrayListOf<Prayer>()

    call.enqueue(object : Callback<PrayerApiResponse> {

        override fun onResponse(call: Call<PrayerApiResponse>, response: Response<PrayerApiResponse>) {

            if (response.isSuccessful) {
                val apiResponse = response.body()
                apiResponse?.result?.forEach { prayer ->
                    prayerList.add(prayer)//liste donecek
                }
                callback(prayerList)
            } else {
                println("Response Error Body: ${response.errorBody()?.string()}")
                callback(arrayListOf())//liste boş donecek
            }
        }

        override fun onFailure(call: Call<PrayerApiResponse>, t: Throwable) {
            println("Network Error: ${t.message}")
            callback(arrayListOf()) //liste boş donecek
        }
    })

}

