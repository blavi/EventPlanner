package com.example.pimpmywed.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pimpmywed.database.GuestsEntity
import com.example.pimpmywed.repository.PersonsRepository
import kotlinx.coroutines.launch

class ViewByTableViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is notifications Fragment"
    }
    val text: LiveData<String> = _text

    var guests = MutableLiveData<List<GuestsEntity>>()

    var dropdown =  MutableLiveData<List<String>>()

    fun getPersonsFromTable(number : Int, forceUpdate : Boolean) {
        viewModelScope.launch {
            val guests = PersonsRepository.getInstance().getGuestsFromTableAsync(number, forceUpdate).await()
//            guests.postValue(guests1)
//            guests = guests1 as MutableLiveData<List<GuestsEntity>>
            updateGuests(guests)
        }
    }

    fun getTables(forceUpdate : Boolean) {
        viewModelScope.launch {
            val tables :List<String> = PersonsRepository.getInstance().getTablesAsync(forceUpdate).await()
            updateTables(tables)
        }
    }

    private fun updateTables(tables : List<String>) {
        val tableValues : List<String> = tables.map { "Table $it" }
        dropdown.postValue(tableValues)
    }

    private fun updateGuests(list: List<GuestsEntity>?) {
        guests.postValue(list)
    }
}