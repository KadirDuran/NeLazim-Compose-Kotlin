package com.example.nelazim.data.repository

import android.content.Context
import com.example.nelazim.data.model.Result
import com.example.nelazim.data.model.WheatherApiResponse
import com.example.nelazim.data.network.CollectApi
import com.example.nelazim.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

fun getWeather(context: Context, data_lang : String, data_city : String, callback: (ArrayList<Result>) -> Unit){
    val apiService = CollectApi.instance
    val call = apiService.getWeather(data_lang, data_city,context.getString(R.string.API_KEY))
    val resultList = arrayListOf<Result>()

    call.enqueue(object : Callback<WheatherApiResponse> {

        override fun onResponse(call: Call<WheatherApiResponse>, response: Response<WheatherApiResponse>) {

            if (response.isSuccessful) {
                val apiResponse = response.body()
                apiResponse?.result?.forEach {result ->
                    resultList.add(result)//liste donecek
                }
                callback(resultList)
            } else {
                println("Response Error Bodys: ${response.errorBody()?.string()}")
                callback(arrayListOf())//liste boş donecek
            }
        }

        override fun onFailure(call: Call<WheatherApiResponse>, t: Throwable) {
            println("Network Error: ${t.message}")
            callback(arrayListOf()) //liste boşs donecek
        }
    })

}

