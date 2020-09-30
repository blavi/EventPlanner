package com.example.pimpmywed.ui

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pimpmywed.adapter.AllGuestsAdapter
import com.example.pimpmywed.database.GuestsEntity
import com.example.pimpmywed.databinding.FragmentNotificationsBinding
import com.example.pimpmywed.utils.Constants
import com.example.pimpmywed.viewmodel.AllTablesViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel


class AllTablesFragment : Fragment() {

    private val allTablesViewModel: AllTablesViewModel  by viewModel()
    private lateinit var binding: FragmentNotificationsBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentNotificationsBinding.inflate(inflater, container, false)

        setUI()
        setupObservers()

        return binding.root
    }

    override fun onResume() {
        super.onResume()

        setCurrentState(false)
    }

    private fun setUI() {
        val animator = binding.guestsList.getItemAnimator()
        if (animator is DefaultItemAnimator) {
            (animator as DefaultItemAnimator).supportsChangeAnimations = false
        }
    }

    private fun setupObservers() {
        allTablesViewModel.guests.observe(viewLifecycleOwner, Observer {
            var adapterAll : AllGuestsAdapter = AllGuestsAdapter(it, { guestItem : GuestsEntity -> guestItemClicked(guestItem) })
            binding.guestsList.layoutManager = LinearLayoutManager(activity)
            binding.guestsList.adapter = adapterAll
        })
    }

    private fun guestItemClicked(guestItem: GuestsEntity) {
        val pop = GuestDetailsDialogFragment()
        val fm = parentFragmentManager

        val bundle = Bundle()
        bundle.putParcelable(Constants.GUEST_KEY, guestItem)
        pop.arguments = bundle

        pop.show(fm, "name")

        fm.executePendingTransactions()
        pop.dialog?.setOnDismissListener(DialogInterface.OnDismissListener {
            lifecycleScope.launch {
                showLoadingDialog()
                delay(1500)
                setCurrentState(true)

                hideLoadingDialog()
            }
        })
    }

    private fun hideLoadingDialog() {
        binding.progressbar.visibility = View.GONE
    }

    private fun showLoadingDialog() {
        binding.progressbar.visibility = View.VISIBLE
    }

    private fun setCurrentState(forceUpdate : Boolean) {
        allTablesViewModel.getPersons(forceUpdate)
    }

}