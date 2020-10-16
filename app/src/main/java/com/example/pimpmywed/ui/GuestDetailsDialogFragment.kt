package com.example.pimpmywed.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import com.example.pimpmywed.R
import com.example.pimpmywed.database.GuestsEntity
import com.example.pimpmywed.databinding.FragmentGuestDetailsBinding
import com.example.pimpmywed.utils.Constants
import com.example.pimpmywed.viewmodel.GuestDetailsViewModel
import org.koin.android.ext.android.bind
import org.koin.androidx.viewmodel.ext.android.viewModel

class GuestDetailsDialogFragment : DialogFragment() {
    private val guestDetailsViewModel: GuestDetailsViewModel by viewModel()
    private lateinit var binding: FragmentGuestDetailsBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = FragmentGuestDetailsBinding.inflate(inflater, container, false)
        binding.guestDetailsViewModel = guestDetailsViewModel
        binding.lifecycleOwner = viewLifecycleOwner

        setUI(arguments?.get(Constants.GUEST_KEY) as GuestsEntity)
        setObservers()

        return binding.root
    }

    private fun setObservers() {
        guestDetailsViewModel.guestStatusChanged.observe(this, Observer {
            this.dialog?.dismiss()
        })
    }

    private fun setUI(guest: GuestsEntity?) {
        if (guest != null) {
            guestDetailsViewModel.guest = guest
        }
    }
}