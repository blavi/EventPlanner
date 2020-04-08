package com.example.pimpmywed.api

import com.example.pimpmywed.utils.Constants
import com.google.api.services.sheets.v4.model.ValueRange

class ApiProvider {

    private var client = RetrofitService.createService(ApiEndpoints::class.java)

    suspend fun getData(range : String) = client.getData(Constants.SHEET_ID, range, Constants.GOOGLE_API_KEY)

    suspend fun updateData(range : String, body : ValueRange) = client.updateData(Constants.SHEET_ID, range, Constants.GOOGLE_API_KEY, body)
}