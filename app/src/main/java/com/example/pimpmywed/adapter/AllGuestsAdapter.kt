package com.example.pimpmywed.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.pimpmywed.R
import com.example.pimpmywed.database.GuestsEntity
import com.example.pimpmywed.model.Table
import com.thoughtbot.expandablerecyclerview.ExpandableRecyclerViewAdapter
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup

class AllGuestsAdapter(groups:List<Table>, private val clickListener: (GuestsEntity) -> Unit): ExpandableRecyclerViewAdapter<TableViewHolder, GuestViewHolder>(groups) {

    override fun onBindGroupViewHolder(holder: TableViewHolder?, flatPosition: Int, group: ExpandableGroup<*>?) {
        holder?.setTableNumber(group as Table)
    }

    override fun onBindChildViewHolder(holder: GuestViewHolder?, flatPosition: Int, group: ExpandableGroup<*>?, childIndex: Int) {
        val artist = (group as Table).items.get(childIndex)
        holder?.onBind(artist, clickListener)
    }

    override fun onCreateGroupViewHolder(parent: ViewGroup, viewType:Int):TableViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.table, parent, false)
        return TableViewHolder(view)
    }

    override fun onCreateChildViewHolder(parent:ViewGroup, viewType:Int):GuestViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.guest, parent, false)
        return GuestViewHolder(view)
    }

}