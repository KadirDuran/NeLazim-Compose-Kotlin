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


data class CurrencyApiResponse(
    val result: List<Currency>
)

data class Currency(
    val name: String,
    val buying: String,
    val selling: String
)
interface GetCurrency {
    @GET("economy/allCurrency")
    fun getCurrency(
        @Header("authorization") authHeader: String
    ): Call<CurrencyApiResponse>
}
object RetrofitClientCurrency {
    private const val BASE_URL = "https://api.collectapi.com/"

    val instance: GetCurrency by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofit.create(GetCurrency::class.java)
    }
}
fun getCurrencys(context: Context, callback: (ArrayList<Currency>) -> Unit){
    val apiService = RetrofitClientCurrency.instance
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

