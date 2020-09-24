package com.example.pimpmywed.repository

import com.example.pimpmywed.PimpMyWedApp
import com.example.pimpmywed.api.ApiProvider
import com.example.pimpmywed.database.GuestsEntity
import com.example.pimpmywed.model.Table
import com.example.pimpmywed.utils.Constants
import com.example.pimpmywed.utils.RepositoryUtil
import com.google.api.client.extensions.android.http.AndroidHttp
import com.google.api.client.json.jackson2.JacksonFactory
import com.google.api.services.sheets.v4.Sheets
import com.google.api.services.sheets.v4.model.ValueRange
import kotlinx.coroutines.*


class PersonsRepository private constructor() {

    companion object {
        // For Singleton instantiation
        @Volatile private var instance: PersonsRepository? = null
        val apiProvider : ApiProvider = ApiProvider()

        fun getInstance() =
            instance ?: synchronized(this) {
                instance ?: PersonsRepository().also { instance = it }
            }
    }

    suspend fun getPersons(forceUpdate: Boolean) : List<GuestsEntity> = withContext(Dispatchers.IO) {
        getDataAndSaveToDB(forceUpdate)
        // GET FROM LOCAL DATABASE
        getDataFromDB()
    }

    suspend fun getGuestsBasedOnQuery(forceUpdate: Boolean, query : String) : List<GuestsEntity> = withContext(Dispatchers.IO) {
        val persons : List<GuestsEntity> = getPersons(forceUpdate)

        val filteredGuests = persons
            .filter {
                it.name.startsWith(query, true)
            }
            .filter {
                !it.confirmed_number.equals("")
            }

        filteredGuests
    }

    suspend fun getTables(forceUpdate: Boolean) : List<String> = withContext(Dispatchers.IO) {
        val persons : List<GuestsEntity> = getPersons(forceUpdate)
        // GET FROM LOCAL DATABASE
        val tables : List<String> = persons
            .asSequence()
            .filter {
                it.table != "0"}
            .filter {
                !it.confirmed_number.equals("")
            }
            .sortedWith (
                compareBy {it.table.toInt()}
            )
            .map { it -> it.table }
            .distinct()
            .toList()

        tables
    }

    suspend fun getGuestsFromTable(number : Int, forceUpdate: Boolean) : List<GuestsEntity> = withContext(Dispatchers.IO) {
        val persons = getPersons(forceUpdate)

        val groupedPersons = persons
            .asSequence()
            .filter {
                it.table.toInt() == number
            }
            .filter {
                !it.confirmed_number.equals("")
            }
            .toList()

        groupedPersons
    }

    suspend fun getGuestsByTables(forceUpdate: Boolean) : List<Table> = withContext(Dispatchers.IO) {
        val persons = getPersons(forceUpdate)
        val groupedPersons = persons
            .asSequence()
            .filter {
                it.table != "0"
            }
            .filter {
                !it.confirmed_number.equals("")
            }
            .sortedWith (
                compareBy {it.table.toInt()}
            )
            .groupBy {
                it.table
            }.map { (key, value) ->
                Table(key, value)
            }
            .toList()
        groupedPersons
    }

    private suspend fun getDataAndSaveToDB(forceUpdate : Boolean)  = withContext(Dispatchers.IO) {
        if (RepositoryUtil.isCacheStale() || forceUpdate) {

            RepositoryUtil.resetCache()

            // FETCH DATA FROM NETWORK
            var list = getDataFromAPI()

            // SAVE TO DB
            saveToDB(list)
        }
    }

//    private fun getPersonsByTablesFromDB() : Deferred<List<Table>>{
//        var db = PimpMyWedApp.db
//
//        var guests =  db?.guestsDAO()?.getGuests()
//
//        return guests ?: ArrayList<GuestsEntity>()
//    }

    private fun getDataFromDB() : List<GuestsEntity> {
        var db = PimpMyWedApp.db

        var guests =  db?.guestsDAO()?.getGuests()

        return guests ?: ArrayList<GuestsEntity>()
    }

    private  fun saveToDB(list : Set<List<String>>) {
        var db = PimpMyWedApp.db

        var dao = db?.guestsDAO()

        dao?.removeAll()

        val guests: List<GuestsEntity> = list.map {
            GuestsEntity(
                0,
                it.get(0),
                it.get(1),
                it.get(2),
                it.get(3),
                it.get(4),
                it.get(5),
                it.get(6),
                it.get(7),
                it.get(8)
            )
        }

        dao?.insertAll(guests)
    }

    private suspend fun getDataFromAPI() : Set<List<String>> = withContext(Dispatchers.IO) {
        try {
            val response1 = async { apiProvider.getData("Rude_Lavi!A3:I41") }
            val response2 = async { apiProvider.getData("Cunostinte_Lavi!A3:I44") }
            val response3 = async { apiProvider.getData("Rude_Dani!A3:I6") }
            val response4 = async { apiProvider.getData("Cunostinte_Dani!A3:I28") }

            val list1 = response1.await().body()!!.values
            val list2 = response2.await().body()!!.values
            val list3 = response3.await().body()!!.values
            val list4 = response4.await().body()!!.values

            var list = list1.union(list2).union(list3).union(list4)

            list

        } catch (exception: Exception) {
            setOf(ArrayList<String>())
        }
    }

    suspend fun updateGuestStatus(body: ValueRange, sheetName: String, row: String) = withContext(Dispatchers.IO)  {

        val jsonFactory = JacksonFactory.getDefaultInstance()
        val httpTransport =  AndroidHttp.newCompatibleTransport()
        val service = Sheets.Builder(httpTransport, jsonFactory, PimpMyWedApp.credentials)
            .setApplicationName("PimpMyWed")
            .build()

        service.spreadsheets().values().update(Constants.SHEET_ID,
            "$sheetName!A$row:I$row", body)
            .setValueInputOption("USER_ENTERED")
            .execute()

    }
}