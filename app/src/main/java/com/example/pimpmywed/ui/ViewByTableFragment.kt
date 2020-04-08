package com.example.pimpmywed.ui

import android.app.ProgressDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ProgressBar
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pimpmywed.R
import com.example.pimpmywed.adapter.TableGuestsAdapter
import com.example.pimpmywed.database.GuestsEntity
import com.example.pimpmywed.utils.Constants
import com.example.pimpmywed.viewmodel.ViewByTableViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ViewByTableFragment : Fragment() {

    private lateinit var viewByTableViewModel: ViewByTableViewModel
    private lateinit var guestsList : RecyclerView
    private lateinit var dropdown : Spinner
    private lateinit var dropdownAdapter : ArrayAdapter<String>
    private var itemNumber : Int = 0
    private lateinit var progressBar : ProgressBar

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewByTableViewModel = ViewModelProvider(this).get(ViewByTableViewModel::class.java)

        val root = inflater.inflate(R.layout.fragment_viewbytable, container, false)
        guestsList= root.findViewById(R.id.guestsList)
        dropdown = root.findViewById(R.id.positionSpinner)
        progressBar = root.findViewById(R.id.progressbar)

        setupObservers()
        setUI()

        return root
    }


    override fun onResume() {
        super.onResume()

        setTables()
    }

    private fun setUI() {
        dropdown.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val item = dropdownAdapter.getItem(position)
                val number = item?.substring(item.indexOf(" ") + 1)
                itemNumber = number?.toInt() ?: 1
                setGuests(itemNumber, false)
            }
        }
    }

    private fun setupObservers() {
        viewByTableViewModel.guests.observe(viewLifecycleOwner, Observer {
            var adapterAll : TableGuestsAdapter = TableGuestsAdapter(it, { guestItem : GuestsEntity -> guestItemClicked(guestItem) })
            guestsList.layoutManager = LinearLayoutManager(activity)
            guestsList.adapter = adapterAll
            guestsList.invalidate()
        })

        viewByTableViewModel.dropdown.observe(viewLifecycleOwner, Observer {
            dropdownAdapter = ArrayAdapter(activity!!.applicationContext, R.layout.spinner_item, it)
            dropdownAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            dropdown.adapter = dropdownAdapter
        })
    }

    private fun guestItemClicked(guestItem: GuestsEntity) {
        val pop = GuestDetailsDialogFragment()
        val fm = fragmentManager!!

        val bundle = Bundle()
        bundle.putParcelable(Constants.GUEST_KEY, guestItem)
        pop.arguments = bundle

        pop.show(fm, "name")

        fm.executePendingTransactions()
        pop.dialog?.setOnDismissListener(DialogInterface.OnDismissListener {
            lifecycleScope.launch {
                showLoadingDialog()
                delay(1500)
                setGuests(itemNumber, true)

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

    private fun setTables() {
        viewByTableViewModel.getTables(false)
    }

    private fun setGuests(table : Int, forceUpdate : Boolean) {
        viewByTableViewModel.getPersonsFromTable(table, forceUpdate)
    }
}