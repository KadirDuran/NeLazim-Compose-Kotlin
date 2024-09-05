package com.example.nelazim.data.repository

import android.content.Context
import androidx.compose.ui.text.toLowerCase
import com.example.nelazim.data.model.Prayer
import com.example.nelazim.data.model.PrayerApiResponse
import com.example.nelazim.data.network.CollectApi
import com.example.nelazim.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

fun getPrayers(context: Context, city : String, callback: (ArrayList<Prayer>) -> Unit){
    val apiService = CollectApi.instance
    val call = apiService.getPrayer(city.toLowerCase(),context.getString(R.string.API_KEY))
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
