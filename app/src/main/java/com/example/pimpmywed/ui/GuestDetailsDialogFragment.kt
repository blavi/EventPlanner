package com.example.pimpmywed.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.pimpmywed.R
import com.example.pimpmywed.database.GuestsEntity
import com.example.pimpmywed.databinding.FragmentGuestDetailsBinding
import com.example.pimpmywed.utils.Constants
import com.example.pimpmywed.viewmodel.GuestDetailsViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class GuestDetailsDialogFragment : DialogFragment() {
    private val guestDetailsViewModel: GuestDetailsViewModel by viewModel()
    private lateinit var binding: FragmentGuestDetailsBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = FragmentGuestDetailsBinding.inflate(inflater, container, false)

        setUI(arguments?.get(Constants.GUEST_KEY) as GuestsEntity)

        return binding.root
    }

    private fun setUI(guest: GuestsEntity?) {
        if (guest != null) {
                binding.name = guest.name
                binding.menu = guest.menu
                binding.age = guest.age
                binding.table = guest.table

                if (guest.isChecked()) {
                    binding.status = R.drawable.ic_not_checked
                    binding.checkInBtn.visibility = View.VISIBLE
                    binding.checkInBtn.setOnClickListener{
                        val launchIntent: Intent? = requireActivity().packageManager.getLaunchIntentForPackage("com.google.android.youtube")
                        if (launchIntent != null) {
                            startActivity(launchIntent)
                        } else {
                            Toast.makeText(activity, "There is no package available in android", Toast.LENGTH_LONG).show()
                        }
                    }
                } else {
                    binding.status = R.drawable.ic_checked
                }
            }
    }
}