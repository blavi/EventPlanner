package com.example.pimpmywed.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pimpmywed.database.GuestsEntity
import com.example.pimpmywed.repository.PersonsRepository
import com.example.pimpmywed.utils.Constants
import com.google.api.services.sheets.v4.model.ValueRange
import kotlinx.coroutines.launch

class GuestDetailsViewModel : ViewModel() {
    fun updateStatus(guest: GuestsEntity) {
        viewModelScope.launch {
            val values: List<List<String?>> =
                listOf(
                    listOf(
                        guest.name,
                        guest.invited_number,
                        guest.confirmed_number,
                        guest.invited,
                        guest.menu,
                        guest.age,
                        guest.table,
                        "1",
                        guest.identifier
                    )
                )
            val body: ValueRange = ValueRange().setValues(values)
            var sheetNumber = guest.identifier.substring(0, guest.identifier.indexOf(":"))
            var sheetName : String = when (sheetNumber) {
                Constants.SHEET_1 -> "Rude_Lavi"
                Constants.SHEET_2 -> "Cunostinte_Lavi"
                Constants.SHEET_3 -> "Rude_Dani"
                else -> "Cunostinte_Dani"
            }
            val row : String = guest.identifier.substring(guest.identifier.indexOf(":") + 1)
            PersonsRepository.getInstance().updateGuestStatus(body, sheetName, row)
        }
    }
}