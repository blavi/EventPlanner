package com.example.pimpmywed.viewmodel

import android.view.View
import android.widget.AdapterView
import androidx.databinding.ObservableInt
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pimpmywed.R
import com.example.pimpmywed.database.GuestsEntity
import com.example.pimpmywed.repository.PersonsRepository
import kotlinx.coroutines.launch


class ViewByTableViewModel(private val personsRepository: PersonsRepository): ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is notifications Fragment"
    }
    val text: LiveData<String> = _text

    var guests = MutableLiveData<List<GuestsEntity>>()

    var dropdown = MutableLiveData<List<String>>()

    var selectedItem = MutableLiveData<Int>()

    fun getPersonsFromTable(number: Int, forceUpdate: Boolean) {
        viewModelScope.launch {
            val guests = personsRepository.getGuestsFromTable(number, forceUpdate)
            updateGuests(guests)
        }
    }

    fun getTables(prefix: String, forceUpdate: Boolean) {
        viewModelScope.launch {
            val tables: List<String> = personsRepository.getTables(forceUpdate)
            updateTables(tables, prefix)
        }
    }

    private fun updateTables(tables: List<String>, prefix: String) {
        dropdown.value = tables.map { "$prefix $it" }
    }

    private fun updateGuests(list: List<GuestsEntity>?) {
        guests.value = list
    }

    fun onSelectItem(parent: AdapterView<*>?, view: View?, pos: Int, id: Long) {
        dropdown.value?.get(pos)?.let {
            val tableNumber = it.substring(it.indexOf(" ") + 1)
            if (tableNumber.isNotEmpty()) {
                getPersonsFromTable(tableNumber.toInt(), false)
                selectedItem.value = tableNumber.toInt()
            }
        }
    }
}