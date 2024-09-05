package com.example.nelazim.data.model


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



