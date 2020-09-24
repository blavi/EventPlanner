package com.example.pimpmywed.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.pimpmywed.databinding.FragmentHomeBinding
import com.example.pimpmywed.viewmodel.HomeViewModel

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var binding: FragmentHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = FragmentHomeBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onResume() {
        super.onResume()

        setupObservers()
        setCurrentState()
    }


    private fun setupObservers() {
        homeViewModel.totalNumberOfInvitedPersons.observe(viewLifecycleOwner, Observer {
            binding.totalOfInvitedPersons = it
        })

        homeViewModel.totalNumberOfGuests.observe(viewLifecycleOwner, Observer {
            binding.totalOfGuests = it
        })

        homeViewModel.totalOfVegetarianGuests.observe(viewLifecycleOwner, Observer {
            binding.totalOfGuestsWithVegetarianMenu = it
        })

        homeViewModel.totalOfClassicGuests.observe(viewLifecycleOwner, Observer {
            binding.totalOfGuestsWithClassicMenu = it
        })

        homeViewModel.totalOfChildrenGuests.observe(viewLifecycleOwner, Observer {
            binding.totalOfGuestsWithChildrenMenu = it
        })

        homeViewModel.totalOfCheckedInGuests.observe(viewLifecycleOwner, Observer {
            binding.totalOfCheckedInGuests = it
        })

        homeViewModel.totalOfNotCheckedInGuests.observe(viewLifecycleOwner, Observer {
            binding.totalOfNotCheckedInGuests = it
        })
    }

    private fun setCurrentState() {
        homeViewModel.getPersons(false)
    }
}