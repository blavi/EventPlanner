package com.example.pimpmywed.adapter

import android.view.View
import android.view.animation.Animation.RELATIVE_TO_SELF
import android.view.animation.RotateAnimation
import com.example.pimpmywed.R
import com.example.pimpmywed.databinding.TableBinding
import com.example.pimpmywed.model.Table
import com.thoughtbot.expandablerecyclerview.viewholders.GroupViewHolder


class TableViewHolder(private val binding: TableBinding) : GroupViewHolder(binding.root) {

    fun setTableNumber(group: Table) {
        binding.tableNumber = itemView.context.getString(R.string.table_label) + " " + group.title
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
        binding.listItemGenreArrow.animation = rotate

        binding.separator.visibility = View.VISIBLE
    }

    private fun animateCollapse() {
        val rotate = RotateAnimation(180.0F, 360.0F, RELATIVE_TO_SELF, 0.5f, RELATIVE_TO_SELF, 0.5f)
        rotate.duration = 300
        rotate.fillAfter = true
        binding.listItemGenreArrow.animation = rotate

        binding.separator.visibility = View.GONE
    }
}