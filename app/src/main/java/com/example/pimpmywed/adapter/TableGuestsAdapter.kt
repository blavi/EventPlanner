package com.example.pimpmywed.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pimpmywed.bindings.BindableAdapter
import com.example.pimpmywed.database.GuestsEntity
import com.example.pimpmywed.databinding.GuestBinding

class TableGuestsAdapter(private val clickListener: (GuestsEntity) -> Unit) : RecyclerView.Adapter<GuestViewHolder>(), BindableAdapter<GuestsEntity> {
    var list = emptyList<GuestsEntity>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GuestViewHolder {
//        val view = LayoutInflater.from(parent.context).inflate(R.layout.guest, parent, false)
//        return GuestViewHolder(view)
        return GuestViewHolder(GuestBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: GuestViewHolder, position: Int) {
        val guest = list[position]
        holder.onBind(guest, clickListener)
    }

    override fun setData(items: List<GuestsEntity>?) {
        items?.let {
            list = it
            notifyDataSetChanged()
        }
    }
}