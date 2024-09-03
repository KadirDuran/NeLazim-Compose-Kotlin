package com.example.nelazim.model

import com.google.gson.annotations.SerializedName


data class DataStrict(
    @SerializedName("il") var il: String,
    @SerializedName("plaka") val plaka: Int,
    @SerializedName("ilceleri") val ilceleri: List<String>
)
