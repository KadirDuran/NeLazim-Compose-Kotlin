package com.example.nelazim.data.repository

import android.content.Context
import com.example.nelazim.data.model.Pharmacy
import com.example.nelazim.data.model.PharmacyApiResponse
import com.example.nelazim.data.network.CollectApi
import com.example.nelazim.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

fun getPharmacy(context: Context, district : String, city : String, callback: (ArrayList<Pharmacy>) -> Unit){
    val apiService = CollectApi.instance
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