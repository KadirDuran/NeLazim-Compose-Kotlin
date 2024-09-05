package com.example.nelazim.data.model

import com.google.gson.annotations.SerializedName

data class PrayerApiResponse(
    @SerializedName("success" ) var success : Boolean?          = null,
    @SerializedName("result"  ) var result  : ArrayList<Prayer> = arrayListOf()
)

data class Prayer(
    @SerializedName("saat"  ) var saat  : String? = null,
    @SerializedName("vakit" ) var vakit : String? = null
)



