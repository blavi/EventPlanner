package com.example.pimpmywed.repository

import com.example.pimpmywed.PimpMyWedApp
import com.example.pimpmywed.api.ApiProvider
import com.example.pimpmywed.database.GuestsDAO
import com.example.pimpmywed.database.GuestsEntity
import com.example.pimpmywed.model.Table
import com.example.pimpmywed.utils.Constants
import com.example.pimpmywed.utils.RepositoryUtil
import com.google.api.client.extensions.android.http.AndroidHttp
import com.google.api.client.json.jackson2.JacksonFactory
import com.google.api.services.sheets.v4.Sheets
import com.google.api.services.sheets.v4.model.UpdateValuesResponse
import com.google.api.services.sheets.v4.model.ValueRange
import kotlinx.coroutines.*


class PersonsRepositoryImpl(private val apiProvider: ApiProvider, private val dao: GuestsDAO): PersonsRepository {

    override suspend fun getPersons(forceUpdate: Boolean) : List<GuestsEntity> = withContext(Dispatchers.IO) {
        getDataAndSaveToDB(forceUpdate)
        // GET FROM LOCAL DATABASE
        getDataFromDB()
    }

    override suspend fun getGuestsBasedOnQuery(forceUpdate: Boolean, query : String) : List<GuestsEntity> = withContext(Dispatchers.IO) {
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

    override suspend fun getTables(forceUpdate: Boolean) : List<String> = withContext(Dispatchers.IO) {
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

    override suspend fun getGuestsFromTable(number : Int, forceUpdate: Boolean) : List<GuestsEntity> = withContext(Dispatchers.IO) {
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

    override suspend fun getGuestsByTables(forceUpdate: Boolean) : List<Table> = withContext(Dispatchers.IO) {
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

    override suspend fun updateGuestStatus(body: ValueRange, sheetName: String, row: String): UpdateValuesResponse = withContext(Dispatchers.IO)  {

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

    private suspend fun getDataAndSaveToDB(forceUpdate : Boolean)  = withContext(Dispatchers.IO) {
        if (RepositoryUtil.isCacheStale() || forceUpdate) {

            RepositoryUtil.resetCache()

            // FETCH DATA FROM NETWORK
            var list = getDataFromAPI()

            // SAVE TO DB
            saveToDB(list)
        }
    }

    private fun getDataFromDB() : List<GuestsEntity> {
        return dao.getGuests()
    }

    private  fun saveToDB(list : Set<List<String>>) {
        dao.removeAll()

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

        dao.insertAll(guests)
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

}