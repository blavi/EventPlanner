package com.example.pimpmywed.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pimpmywed.R
import com.example.pimpmywed.database.GuestsEntity

class TableGuestsAdapter(val list: List<GuestsEntity>, private val clickListener: (GuestsEntity) -> Unit) : RecyclerView.Adapter<GuestViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GuestViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.guest, parent, false)
        return GuestViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: GuestViewHolder, position: Int) {
        val guest = list.get(position)
        holder.onBind(guest, clickListener)
    }
}