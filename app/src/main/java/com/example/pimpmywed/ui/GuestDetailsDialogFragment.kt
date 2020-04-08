package com.example.pimpmywed.ui

import android.app.Activity
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.pimpmywed.R
import com.example.pimpmywed.database.GuestsEntity
import com.example.pimpmywed.utils.Constants
import com.example.pimpmywed.viewmodel.GuestDetailsViewModel
import kotlinx.coroutines.launch


class GuestDetailsDialogFragment : DialogFragment() {
    private lateinit var guestDetailsViewModel: GuestDetailsViewModel
    private lateinit var name : TextView
    private lateinit var menu : TextView
    private lateinit var table : TextView
    private lateinit var age : TextView
    private lateinit var status : ImageView
    private lateinit var checkInBtn : Button

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        guestDetailsViewModel = ViewModelProvider(this).get(GuestDetailsViewModel::class.java)

        var myView = inflater.inflate(R.layout.fragment_guest_details, container, false)

        name = myView.findViewById(R.id.nameVal)
        menu = myView.findViewById(R.id.menuVal)
        age = myView.findViewById(R.id.ageVal)
        table = myView.findViewById(R.id.tableVal)
        status = myView.findViewById(R.id.statusVal)
        checkInBtn = myView.findViewById(R.id.checkInBtn)

        setUI(arguments?.get(Constants.GUEST_KEY) as GuestsEntity)

        return myView
    }

    private fun setUI(guest: GuestsEntity?) {
        if (guest != null) {
                name.text = guest.name
                menu.text = guest.menu
                age.text = guest.age
                table.text = guest.table

                if (guest.checked.equals("0")) {
                    status.setImageResource(R.drawable.ic_not_checked)
                    checkInBtn.visibility = View.VISIBLE
                    checkInBtn.setOnClickListener{
                        guestDetailsViewModel.updateStatus(guest)
                        this.dialog?.dismiss()
                    }
                } else {
                    status.setImageResource(R.drawable.ic_checked)
                }
            }
    }
}