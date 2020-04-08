package com.example.pimpmywed.api

import com.example.pimpmywed.model.SheetResponse
import com.google.api.services.sheets.v4.model.ValueRange
import retrofit2.Response
import retrofit2.http.*

interface ApiEndpoints {

    @GET("{sheetID}/values/{range}")
    suspend fun getData(@Path("sheetID") sheetID: String, @Path("range") range: String, @Query("key") apiKey: String) : Response<SheetResponse>

    @PUT("{sheetID}/values/{range}")
    suspend fun updateData(@Path("sheetID") sheetId: String, @Path("range") range: String, @Query("key") googleApiKey: String, @Body body: ValueRange) : Any
}