package com.example.pimpmywed.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.example.pimpmywed.R
import com.example.pimpmywed.database.GuestsEntity
import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder

class GuestViewHolder(itemView : View): ChildViewHolder(itemView) {
    private val guestName : TextView
    private val checkedStatus : ImageView
    init{
        guestName = itemView.findViewById(R.id.list_item_guest_name)
        checkedStatus = itemView.findViewById(R.id.checked_status)
    }
    fun onBind(guest : GuestsEntity, clickListener: (GuestsEntity) -> Unit) {
        guestName.setText(guest.name)
        if (guest.checked.toInt() == 0) {
            checkedStatus.setImageResource(R.drawable.ic_not_checked)
        } else {
            checkedStatus.setImageResource(R.drawable.ic_checked)
        }

        itemView.setOnClickListener{
            clickListener(guest)
        }
    }
}