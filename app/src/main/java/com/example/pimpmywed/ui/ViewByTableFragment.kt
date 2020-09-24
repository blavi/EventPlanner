package com.example.pimpmywed.ui

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pimpmywed.R
import com.example.pimpmywed.adapter.TableGuestsAdapter
import com.example.pimpmywed.database.GuestsEntity
import com.example.pimpmywed.databinding.FragmentViewbytableBinding
import com.example.pimpmywed.utils.Constants
import com.example.pimpmywed.viewmodel.ViewByTableViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ViewByTableFragment : Fragment() {

    private lateinit var viewByTableViewModel: ViewByTableViewModel
    private lateinit var binding: FragmentViewbytableBinding

    private lateinit var dropdownAdapter : ArrayAdapter<String>
    private var itemNumber : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewByTableViewModel = ViewModelProvider(this).get(ViewByTableViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentViewbytableBinding.inflate(inflater, container, false)

        setupObservers()
        setUI()

        return binding.root
    }


    override fun onResume() {
        super.onResume()

        setTables()
    }

    private fun setUI() {
        binding.positionSpinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
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
            binding.guestsList.layoutManager = LinearLayoutManager(activity)
            binding.guestsList.adapter = adapterAll
            binding.guestsList.invalidate()
        })

        viewByTableViewModel.dropdown.observe(viewLifecycleOwner, Observer { list ->
            val tableValues : List<String> = list.map { getString(R.string.table1 ) + " " + it }
            dropdownAdapter = ArrayAdapter(requireContext(), R.layout.spinner_item, tableValues)
            dropdownAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.positionSpinner.adapter = dropdownAdapter
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
                setGuests(itemNumber, true)

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

    private fun setTables() {
        viewByTableViewModel.getTables(false)
    }

    private fun setGuests(table : Int, forceUpdate : Boolean) {
        viewByTableViewModel.getPersonsFromTable(table, forceUpdate)
    }
}