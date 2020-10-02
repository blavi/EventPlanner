package com.example.pimpmywed.adapter

import com.example.pimpmywed.R
import com.example.pimpmywed.database.GuestsEntity
import com.example.pimpmywed.databinding.GuestBinding
import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder

class GuestViewHolder(private val binding: GuestBinding): ChildViewHolder(binding.root) {
//    private val guestName : TextView
//    private val checkedStatus : ImageView
//    init{
//        guestName = itemView.findViewById(R.id.list_item_guest_name)
//        checkedStatus = itemView.findViewById(R.id.checked_status)
//    }
    fun onBind(guest : GuestsEntity, clickListener: (GuestsEntity) -> Unit) {

        binding.guest = guest
//        binding.guestName = guest.name
//            if (guest.checked.toInt() == 0) {
//                binding.guestStatus = R.drawable.ic_not_checked
//            } else {
//                binding.guestStatus = R.drawable.ic_checked
//            }
//
        itemView.setOnClickListener{
            clickListener(guest)
        }
        binding.executePendingBindings()
    }
}