package com.example.pimpmywed.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.pimpmywed.databinding.FragmentDashboardBinding
import com.example.pimpmywed.viewmodel.DashboardViewModel

class DashboardFragment : Fragment() {

    private lateinit var dashboardViewModel: DashboardViewModel
    private lateinit var binding: FragmentDashboardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        dashboardViewModel = ViewModelProvider(this).get(DashboardViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = FragmentDashboardBinding.inflate(inflater, container, false)


        val root = binding.root

        dashboardViewModel.text.observe(viewLifecycleOwner, Observer {
            binding.numberOfDaysVal = it.days.toString()
            binding.numberOfHoursVal = it.hours.toString()
            binding.numberOfMinutesVal = it.minutes.toString()
            binding.numberOfSecondsVal = it.seconds.toString()
        })

        return binding.root
    }
}