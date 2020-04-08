package com.example.pimpmywed.adapter

import android.provider.Settings.Global.getString
import android.view.View
import android.view.animation.Animation.RELATIVE_TO_SELF
import android.view.animation.RotateAnimation
import android.widget.ImageView
import android.widget.TextView
import com.example.pimpmywed.R
import com.example.pimpmywed.model.Table
import com.thoughtbot.expandablerecyclerview.viewholders.GroupViewHolder


class TableViewHolder(itemView: View) : GroupViewHolder(itemView) {
    private val tableNumber : TextView
    private val arrow : ImageView
    private val separator : View

    init{
        tableNumber = itemView.findViewById(R.id.list_item_table_name)
        arrow = itemView.findViewById(R.id.list_item_genre_arrow)
        separator = itemView.findViewById(R.id.separator)
    }

    fun setTableNumber(group: Table) {
        tableNumber.text = itemView.context.getString(R.string.table_label) + " " + group.getTitle()
    }

    override fun expand() {
        animateExpand()
    }

    override fun collapse() {
        animateCollapse()
    }

    private fun animateExpand() {
        val rotate = RotateAnimation(360.0F, 180.0F, RELATIVE_TO_SELF, 0.5f, RELATIVE_TO_SELF, 0.5f)
        rotate.duration = 300
        rotate.fillAfter = true
        arrow.setAnimation(rotate)

        separator.visibility = View.VISIBLE
    }

    private fun animateCollapse() {
        val rotate = RotateAnimation(180.0F, 360.0F, RELATIVE_TO_SELF, 0.5f, RELATIVE_TO_SELF, 0.5f)
        rotate.duration = 300
        rotate.fillAfter = true
        arrow.animation = rotate

        separator.visibility = View.GONE
    }
}