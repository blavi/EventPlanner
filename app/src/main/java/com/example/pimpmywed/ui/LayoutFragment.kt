package com.example.pimpmywed.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.example.pimpmywed.R
import com.example.pimpmywed.adapter.LayoutPagerAdapter
import com.google.android.material.tabs.TabLayout

class LayoutFragment : Fragment() {
    private lateinit var viewpager : ViewPager
    private lateinit var tabs : TabLayout

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val root = inflater.inflate(R.layout.fragment_positions, container, false)

        viewpager = root.findViewById(R.id.viewpager_main)
        tabs = root.findViewById(R.id.tabs_main)

        val fragmentAdapter = LayoutPagerAdapter(activity!!.supportFragmentManager, activity!!.applicationContext)
        viewpager.adapter = fragmentAdapter

        tabs.setupWithViewPager(viewpager)

        return root
    }

    override fun onResume() {
        super.onResume()
    }
}