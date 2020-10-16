package com.example.pimpmywed.adapter

import com.example.pimpmywed.database.GuestsEntity
import com.example.pimpmywed.databinding.GuestBinding
import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder

class GuestViewHolder(private val binding: GuestBinding): ChildViewHolder(binding.root) {
    fun onBind(guest : GuestsEntity, clickListener: (GuestsEntity) -> Unit) {

        binding.guest = guest
        itemView.setOnClickListener{
            clickListener(guest)
        }
        binding.executePendingBindings()
    }
}