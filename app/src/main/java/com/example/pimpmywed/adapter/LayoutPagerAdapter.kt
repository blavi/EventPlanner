package com.example.pimpmywed.adapter

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.example.pimpmywed.R
import com.example.pimpmywed.ui.AllTablesFragment
import com.example.pimpmywed.ui.ViewByTableFragment

class LayoutPagerAdapter(fm : FragmentManager, val context : Context) : FragmentStatePagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> ViewByTableFragment()
            else -> return AllTablesFragment()
        }
    }

    override fun getCount(): Int {
        return 2
    }

    override fun getPageTitle(position: Int): CharSequence {
        return when (position) {
            0 -> context.getString(R.string.view_by_table)
            else -> return context.getString(R.string.all_tables)
        }
    }
}