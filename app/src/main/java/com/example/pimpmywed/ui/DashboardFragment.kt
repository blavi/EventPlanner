package com.example.pimpmywed.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.pimpmywed.databinding.FragmentDashboardBinding
import com.example.pimpmywed.viewmodel.DashboardViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class DashboardFragment : Fragment() {

    private val dashboardViewModel: DashboardViewModel by viewModel()
    private lateinit var binding: FragmentDashboardBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentDashboardBinding.inflate(inflater, container, false)

        dashboardViewModel.text.observe(viewLifecycleOwner, Observer {
            binding.numberOfDaysVal = it.days.toString()
            binding.numberOfHoursVal = it.hours.toString()
            binding.numberOfMinutesVal = it.minutes.toString()
            binding.numberOfSecondsVal = it.seconds.toString()
        })

        return binding.root
    }
}