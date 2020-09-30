package com.example.pimpmywed.repository

import com.example.pimpmywed.database.GuestsEntity
import com.example.pimpmywed.model.Table
import com.google.api.services.sheets.v4.model.UpdateValuesResponse
import com.google.api.services.sheets.v4.model.ValueRange

interface PersonsRepository {
    suspend fun getPersons(forceUpdate: Boolean) : List<GuestsEntity>

    suspend fun getGuestsBasedOnQuery(forceUpdate: Boolean, query : String) : List<GuestsEntity>

    suspend fun getTables(forceUpdate: Boolean) : List<String>

    suspend fun getGuestsFromTable(number : Int, forceUpdate: Boolean) : List<GuestsEntity>

    suspend fun getGuestsByTables(forceUpdate: Boolean) : List<Table>

//    suspend fun getDataAndSaveToDB(forceUpdate : Boolean)

//    suspend fun getDataFromAPI() : Set<List<String>>

    suspend fun updateGuestStatus(body: ValueRange, sheetName: String, row: String): UpdateValuesResponse

//    fun getDataFromDB()
//
//    fun saveToDB(list : Set<List<String>>)
}