package com.example.pimpmywed.ui

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pimpmywed.R
import com.example.pimpmywed.adapter.AllGuestsAdapter
import com.example.pimpmywed.database.GuestsEntity
import com.example.pimpmywed.utils.Constants
import com.example.pimpmywed.viewmodel.AllTablesViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class AllTablesFragment : Fragment() {

    private lateinit var allTablesViewModel: AllTablesViewModel
    private lateinit var guestsList : RecyclerView
    private lateinit var progressBar : ProgressBar

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        allTablesViewModel = ViewModelProvider(this).get(AllTablesViewModel::class.java)

        val root = inflater.inflate(R.layout.fragment_notifications, container, false)
        guestsList= root.findViewById(R.id.guestsList)
        progressBar = root.findViewById(R.id.progressbar)

        setUI()
        setupObservers()

        return root
    }

    override fun onResume() {
        super.onResume()

        setCurrentState(false)
    }

    private fun setUI() {
        val animator = guestsList.getItemAnimator()
        if (animator is DefaultItemAnimator) {
            (animator as DefaultItemAnimator).setSupportsChangeAnimations(false)
        }
    }

    private fun setupObservers() {
        allTablesViewModel.guests.observe(viewLifecycleOwner, Observer {
            var adapterAll : AllGuestsAdapter = AllGuestsAdapter(it, { guestItem : GuestsEntity -> guestItemClicked(guestItem) })
            guestsList.setLayoutManager(LinearLayoutManager(activity))
            guestsList.setAdapter(adapterAll)
        })
    }

    private fun guestItemClicked(guestItem: GuestsEntity) {
        val pop = GuestDetailsDialogFragment()
        val fm = fragmentManager!!

        val bundle = Bundle()
        bundle.putParcelable(Constants.GUEST_KEY, guestItem)
        pop.arguments = bundle

        pop.show(fm, "name")

        fm.executePendingTransactions();
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
        progressBar.visibility = View.GONE
    }

    private fun showLoadingDialog() {
        progressBar.visibility = View.VISIBLE
    }

    private fun setCurrentState(forceUpdate : Boolean) {
        allTablesViewModel.getPersons(forceUpdate)
    }

}