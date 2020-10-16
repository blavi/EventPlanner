package com.example.pimpmywed.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.pimpmywed.adapter.LayoutPagerAdapter
import com.example.pimpmywed.databinding.FragmentPositionsBinding

class LayoutFragment : Fragment() {

    private lateinit var binding: FragmentPositionsBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentPositionsBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner

        val fragmentAdapter = LayoutPagerAdapter(requireActivity().supportFragmentManager, requireContext())
        binding.viewpagerMain.adapter = fragmentAdapter
        binding.tabsMain.setupWithViewPager(binding.viewpagerMain)

        return binding.root
    }
}