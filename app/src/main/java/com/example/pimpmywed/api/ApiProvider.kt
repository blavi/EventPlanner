package com.example.pimpmywed.api

import com.example.pimpmywed.utils.Constants
import com.google.api.services.sheets.v4.model.ValueRange

class ApiProvider(private val api: ApiEndpoints){

    suspend fun getData(range : String) = api.getData(Constants.SHEET_ID, range, Constants.GOOGLE_API_KEY)

    suspend fun updateData(range : String, body : ValueRange) = api.updateData(Constants.SHEET_ID, range, Constants.GOOGLE_API_KEY, body)
}