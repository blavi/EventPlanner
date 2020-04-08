package com.example.pimpmywed.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.example.pimpmywed.api.ApiProvider
import com.example.pimpmywed.database.GuestsEntity
import com.example.pimpmywed.repository.PersonsRepository
import com.example.pimpmywed.utils.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel(application: Application) : AndroidViewModel(application) {
//    private val klaxon = Klaxon()
//    private var persons : List<Guest>
    private var apiProvider: ApiProvider = ApiProvider()

    var totalNumberOfGuests = MutableLiveData<String>()

    var totalNumberOfInvitedPersons = MutableLiveData<String>()

    var totalOfVegetarianGuests = MutableLiveData<String>()

    var totalOfClassicGuests = MutableLiveData<String>()

    var totalOfChildrenGuests = MutableLiveData<String>()

    var totalOfCheckedInGuests = MutableLiveData<String>()

    var totalOfNotCheckedInGuests = MutableLiveData<String>()

//    init {
//        persons = klaxon.parseArray<Guest>(application.assets.open("invitati.json"))!!

//        getDetails()
//    }

     fun getPersons(forceUpdate : Boolean) {
         viewModelScope.launch {
             updateUI(PersonsRepository.getInstance().getPersonsAsync(forceUpdate).await())
         }
    }

    private fun updateUI(list: List<GuestsEntity>?) {
        if (!list.isNullOrEmpty()) {
            // LIVE DATA
             totalNumberOfInvitedPersons = liveData<String>(Dispatchers.IO) {
                  emit( (list.sumBy { it.invited_number.toInt()}).toString() )
             } as MutableLiveData<String>

            totalNumberOfInvitedPersons.postValue(
                (list.sumBy { it.invited_number.toInt()}).toString()
            )

            totalNumberOfGuests.postValue(
                (list.filter {
                    it.confirmed_number != ""
                }.sumBy { it.confirmed_number.toInt() }).toString()
            )

            totalOfClassicGuests.postValue(
                (list.filter {
                    it.menu.equals(Constants.CLASSIC_MENU)
                }.sumBy { it.confirmed_number.toInt() }).toString()
            )

            totalOfVegetarianGuests.postValue(
                (list.filter {
                    it.menu.equals(Constants.VEGETARIAN_MENU)
                }.sumBy { it.confirmed_number.toInt() }).toString()
            )

            totalOfChildrenGuests.postValue(
                (list.filter {
                    it.menu.equals(Constants.CHILDREN_MENU)
                }.sumBy { it.confirmed_number.toInt() }).toString()
            )

            totalOfCheckedInGuests.postValue(
                (list
                    .filter {
                        it.confirmed_number != ""
                    }
                    .filter {
                        it.checked.equals(Constants.CHECKED_IN)
                    }
                    .sumBy { it.confirmed_number.toInt() }).toString()
            )

            totalOfNotCheckedInGuests.postValue(
                (list
                    .filter {
                        it.confirmed_number != ""
                    }
                    .filter {
                        it.checked.equals(Constants.NOT_CHECKED_IN)
                    }
                    .sumBy { it.confirmed_number.toInt() }).toString()
            )


        } else {
            totalNumberOfInvitedPersons.postValue(
                "0"
            )

            totalNumberOfGuests.postValue(
                "0"
            )
        }
    }
}







//    var totalNumberOfInvitedPersons: MutableLiveData<String> = MutableLiveData<String>().apply {
//        value = persons?.sumBy { it.invitat_numar!!.toInt() }.toString()
//    }

//    var totalNumberOfGuests: MutableLiveData<String> = MutableLiveData<String>().apply {
//        val guests = persons.filter { guest ->
//            guest.confirmat != null
//        }
//        value = guests.sumBy { it.confirmat!!.toInt() }.toString()
//    }


