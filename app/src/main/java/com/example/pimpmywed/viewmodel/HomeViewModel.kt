package com.example.pimpmywed.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pimpmywed.database.GuestsEntity
import com.example.pimpmywed.repository.PersonsRepository
import com.example.pimpmywed.utils.Constants
import kotlinx.coroutines.launch

class HomeViewModel(private val personsRepository: PersonsRepository) : ViewModel() {
//    private val klaxon = Klaxon()
//    private var persons : List<Guest>
//    private var apiProvider: ApiProvider = ApiProvider()

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
             updateUI(personsRepository.getPersons(forceUpdate))
         }
    }

    private fun updateUI(list: List<GuestsEntity>?) {
        if (!list.isNullOrEmpty()) {
            // LIVE DATA
//             totalNumberOfInvitedPersons = liveData<String> {
//                  emit( (list.sumBy { it.invited_number.toInt()}).toString() )
//             } as MutableLiveData<String>

            totalNumberOfInvitedPersons.value = list.sumBy { it.invited_number.toInt()}
                                                    .toString()

            totalNumberOfGuests.value = list.filter { it.confirmed_number != "" }
                                            .sumBy { it.confirmed_number.toInt() }
                                            .toString()


            totalOfClassicGuests.value = list.filter { it.menu.equals(Constants.CLASSIC_MENU) }
                                             .sumBy { it.confirmed_number.toInt() }
                                             .toString()


            totalOfVegetarianGuests.value = list.filter { it.menu.equals(Constants.VEGETARIAN_MENU) }
                                                .sumBy { it.confirmed_number.toInt() }
                                                .toString()


            totalOfChildrenGuests.value = list.filter { it.menu.equals(Constants.CHILDREN_MENU) }
                                              .sumBy { it.confirmed_number.toInt() }
                                              .toString()


            totalOfCheckedInGuests.value = list.filter { it.confirmed_number != "" }
                                               .filter { it.checked.equals(Constants.CHECKED_IN) }
                                               .sumBy { it.confirmed_number.toInt() }.toString()

            totalOfNotCheckedInGuests.value = list.filter {it.confirmed_number != "" }
                                                  .filter { it.checked.equals(Constants.NOT_CHECKED_IN) }
                                                  .sumBy { it.confirmed_number.toInt() }.toString()
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


