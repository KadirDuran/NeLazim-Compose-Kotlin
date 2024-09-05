package com.example.nelazim.data.repository

import android.util.Log
import com.example.nelazim.data.model.DataStrict
import com.example.nelazim.data.network.GithubApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

suspend fun getCityDistrict(): List<DataStrict> {
    return withContext(Dispatchers.IO) {
        try {
            GithubApi.apiService.getCityDistrict()
        } catch (e: Exception) {
            Log.e("Error", "Error fetching data", e)
            emptyList()
        }
    }
}