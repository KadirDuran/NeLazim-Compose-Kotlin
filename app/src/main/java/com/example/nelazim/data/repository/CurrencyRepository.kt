package com.example.nelazim.data.repository

import android.content.Context
import com.example.nelazim.data.model.Currency
import com.example.nelazim.data.model.CurrencyApiResponse
import com.example.nelazim.data.network.CollectApi
import com.example.nelazim.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

fun getCurrencys(context: Context, callback: (ArrayList<Currency>) -> Unit){
    val apiService = CollectApi.instance
    val call = apiService.getCurrency(context.getString(R.string.API_KEY))
    val currencyList = arrayListOf<Currency>()

    call.enqueue(object : Callback<CurrencyApiResponse> {

        override fun onResponse(call: Call<CurrencyApiResponse>, response: Response<CurrencyApiResponse>) {

            if (response.isSuccessful) {
                val apiResponse = response.body()
                apiResponse?.result?.forEach {
                        currency ->
                    currencyList.add(currency)
                }
                callback(currencyList)
            } else {
                println("Response Error Body: ${response.errorBody()?.string()}")
                callback(arrayListOf())//liste boş donecek
            }
        }

        override fun onFailure(call: Call<CurrencyApiResponse>, t: Throwable) {
            println("Network Error: ${t.message}")
            callback(arrayListOf()) //liste boş donecek
        }
    })

}