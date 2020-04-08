package com.example.pimpmywed.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.pimpmywed.R
import com.example.pimpmywed.viewmodel.HomeViewModel

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel

    private lateinit var totalOfInvitedPersons : TextView
    private lateinit var totalOfGuests : TextView
    private lateinit var totalOfVegetarianGuests : TextView
    private lateinit var totalOfClassicGuests : TextView
    private lateinit var totalOfChildrenGuests : TextView
    private lateinit var totalOfCheckedInGuests : TextView
    private lateinit var totalOfNotCheckedInGuests : TextView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        val root = inflater.inflate(R.layout.fragment_home, container, false)
        totalOfInvitedPersons = root.findViewById(R.id.totalOfInvitedPersonsVal)
        totalOfGuests = root.findViewById(R.id.totalOfGuestssVal)
        totalOfVegetarianGuests = root.findViewById(R.id.totalOfGuestsWithVegetarianMenuVal)
        totalOfClassicGuests = root.findViewById(R.id.totalOfGuestsWithClassicMenuVal)
        totalOfChildrenGuests = root.findViewById(R.id.totalOfGuestsWithChildrenMenuVal)
        totalOfCheckedInGuests = root.findViewById(R.id.totalOfCheckedInGuestsVal)
        totalOfNotCheckedInGuests = root.findViewById(R.id.totalOfNotCheckedInGuestsVal)

        return root
    }

    override fun onResume() {
        super.onResume()

        setupObservers()
        setCurrentState()
    }


    private fun setupObservers() {
        homeViewModel.totalNumberOfInvitedPersons.observe(viewLifecycleOwner, Observer {
            totalOfInvitedPersons.text = it
        })

        homeViewModel.totalNumberOfGuests.observe(viewLifecycleOwner, Observer {
            totalOfGuests.text = it
        })

        homeViewModel.totalOfVegetarianGuests.observe(viewLifecycleOwner, Observer {
            totalOfVegetarianGuests.text = it
        })

        homeViewModel.totalOfClassicGuests.observe(viewLifecycleOwner, Observer {
            totalOfClassicGuests.text = it
        })

        homeViewModel.totalOfChildrenGuests.observe(viewLifecycleOwner, Observer {
            totalOfChildrenGuests.text = it
        })

        homeViewModel.totalOfCheckedInGuests.observe(viewLifecycleOwner, Observer {
            totalOfCheckedInGuests.text = it
        })

        homeViewModel.totalOfNotCheckedInGuests.observe(viewLifecycleOwner, Observer {
            totalOfNotCheckedInGuests.text = it
        })
    }

    private fun setCurrentState() {
        homeViewModel.getPersons(false)
    }
}