package com.example.pimpmywed.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pimpmywed.database.GuestsEntity
import com.example.pimpmywed.repository.PersonsRepository
import com.example.pimpmywed.utils.Constants
import com.google.api.services.sheets.v4.model.ValueRange
import kotlinx.coroutines.launch

class GuestDetailsViewModel(private val personsRepository: PersonsRepository) : ViewModel() {
    var guest: GuestsEntity? = null
    var guestStatusChanged = MutableLiveData<Boolean>()

    fun updateStatus() {
        guest?.let {
            viewModelScope.launch {
                val values: List<List<String?>> =
                    listOf(
                        listOf(
                            it.name,
                            it.invited_number,
                            it.confirmed_number,
                            it.invited,
                            it.menu,
                            it.age,
                            it.table,
                            "1",
                            it.identifier
                        )
                    )
                val body: ValueRange = ValueRange().setValues(values)
                var sheetNumber = it.identifier.substring(0, it.identifier.indexOf(":"))
                var sheetName: String = when (sheetNumber) {
                    Constants.SHEET_1 -> "Rude_Lavi"
                    Constants.SHEET_2 -> "Cunostinte_Lavi"
                    Constants.SHEET_3 -> "Rude_Dani"
                    else -> "Cunostinte_Dani"
                }
                val row: String = it.identifier.substring(it.identifier.indexOf(":") + 1)
                personsRepository.updateGuestStatus(body, sheetName, row)

                guestStatusChanged.value = true
            }
        }
    }
}