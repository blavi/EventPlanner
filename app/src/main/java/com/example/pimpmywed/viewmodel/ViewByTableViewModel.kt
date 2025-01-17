package com.example.pimpmywed.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pimpmywed.database.GuestsEntity
import com.example.pimpmywed.repository.PersonsRepository
import kotlinx.coroutines.launch


class ViewByTableViewModel(private val personsRepository: PersonsRepository): ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is notifications Fragment"
    }
    val text: LiveData<String> = _text

    var guests = MutableLiveData<List<GuestsEntity>>()

    var dropdown =  MutableLiveData<List<String>>()

    fun getPersonsFromTable(number : Int, forceUpdate : Boolean) {
        viewModelScope.launch {
            val guests = personsRepository.getGuestsFromTable(number, forceUpdate)
            updateGuests(guests)
        }
    }

    fun getTables(forceUpdate : Boolean) {
        viewModelScope.launch {
            val tables :List<String> = personsRepository.getTables(forceUpdate)
            updateTables(tables)
        }
    }

    private fun updateTables(tables : List<String>) {
        dropdown.value = tables
    }

    private fun updateGuests(list: List<GuestsEntity>?) {
        guests.value = list
    }
}