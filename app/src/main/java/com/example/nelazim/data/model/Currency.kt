package com.example.nelazim.data.model

data class CurrencyApiResponse(
    val result: List<Currency>
)

data class Currency(
    val name: String,
    val buying: String,
    val selling: String
)



