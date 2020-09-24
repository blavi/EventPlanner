package com.example.pimpmywed.viewmodel

import android.R
import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pimpmywed.database.GuestsEntity
import com.example.pimpmywed.repository.PersonsRepository
import kotlinx.coroutines.launch


class ViewByTableViewModel: ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is notifications Fragment"
    }
    val text: LiveData<String> = _text

    var guests = MutableLiveData<List<GuestsEntity>>()

    var dropdown =  MutableLiveData<List<String>>()

    fun getPersonsFromTable(number : Int, forceUpdate : Boolean) {
        viewModelScope.launch {
            val guests = PersonsRepository.getInstance().getGuestsFromTable(number, forceUpdate)
            updateGuests(guests)
        }

//        guests = liveData {
//            val guests = PersonsRepository.getInstance().getGuestsFromTableAsync(number, forceUpdate).await()
//            emit(guests)
//        } as MutableLiveData<List<GuestsEntity>>
    }

    fun getTables(forceUpdate : Boolean) {
        viewModelScope.launch {
            val tables :List<String> = PersonsRepository.getInstance().getTables(forceUpdate)
            updateTables(tables)
        }
    }

    private fun updateTables(tables : List<String>) {
//        contentString.set()
//        val tableValues : List<String> = tables.map { "Table $it" }
        dropdown.value = tables
    }

    private fun updateGuests(list: List<GuestsEntity>?) {
        guests.value = list
    }
}