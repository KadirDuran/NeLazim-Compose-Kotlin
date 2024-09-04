package com.example.nelazim.model
import com.google.gson.annotations.SerializedName
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


data class WheatherApiResponse(
    @SerializedName("success" ) var success : Boolean?          = null,
    @SerializedName("city"    ) var city    : String?           = null,
    @SerializedName("result"  ) var result  : ArrayList<Result> = arrayListOf()
)

data class Result (

    @SerializedName("date"        ) var date        : String? = null,
    @SerializedName("day"         ) var day         : String? = null,
    @SerializedName("icon"        ) var icon        : String? = null,
    @SerializedName("description" ) var description : String? = null,
    @SerializedName("status"      ) var status      : String? = null,
    @SerializedName("degree"      ) var degree      : String? = null,
    @SerializedName("min"         ) var min         : String? = null,
    @SerializedName("max"         ) var max         : String? = null,
    @SerializedName("night"       ) var night       : String? = null,
    @SerializedName("humidity"    ) var humidity    : String? = null

)
interface GetWeather {
    @GET("weather/getWeather")
    fun getWeather(
        @Query("data.lang") data_lang: String,
        @Query("data.city") data_city: String,
        @Header("authorization") authHeader: String
    ): Call<WheatherApiResponse>
}
object RetrofitClientWeather {
    private const val BASE_URL = "https://api.collectapi.com/"

    val instance: GetWeather by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofit.create(GetWeather::class.java)
    }
}
fun getWeather(context: Context, data_lang : String, data_city : String, callback: (ArrayList<Result>) -> Unit){
    val apiService = RetrofitClientWeather.instance
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
                println("Response Error Body: ${response.errorBody()?.string()}")
                callback(arrayListOf())//liste boş donecek
            }
        }

        override fun onFailure(call: Call<WheatherApiResponse>, t: Throwable) {
            println("Network Error: ${t.message}")
            callback(arrayListOf()) //liste boş donecek
        }
    })

}

