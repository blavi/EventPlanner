package com.example.pimpmywed.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pimpmywed.model.Table
import com.example.pimpmywed.repository.PersonsRepository
import kotlinx.coroutines.launch

class AllTablesViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is notifications Fragment"
    }
    val text: LiveData<String> = _text

    var guests = MutableLiveData<List<Table>>()

    fun getPersons(forceUpdate : Boolean) {
        viewModelScope.launch {
            val list = PersonsRepository.getInstance().getGuestsByTablesAsync(forceUpdate).await()
            updateUI(list)
        }
    }

    private fun updateUI(list: List<Table>?) {
        guests.postValue(
            list
        )
    }
}