package com.example.pimpmywed.ui

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
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
import org.koin.androidx.viewmodel.ext.android.viewModel

class ViewByTableFragment : Fragment() {

    private val viewByTableViewModel: ViewByTableViewModel by viewModel()
    private lateinit var binding: FragmentViewbytableBinding

    private lateinit var dropdownAdapter : ArrayAdapter<String>
    private var itemNumber : Int = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentViewbytableBinding.inflate(inflater, container, false)

        setupObservers()

        binding.viewByTableViewModel = viewByTableViewModel
        binding.lifecycleOwner = viewLifecycleOwner

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerViewAdapter = TableGuestsAdapter { guestItem: GuestsEntity -> guestItemClicked(guestItem) }
        binding.guestsList.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = recyclerViewAdapter
        }

        dropdownAdapter = ArrayAdapter(requireContext(), R.layout.spinner_item)
        dropdownAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.positionSpinner.adapter = dropdownAdapter

//        dropdownAdapter = DropdownAdapter(requireContext(), R.layout.spinner_item)
//        dropdownAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
//        binding.positionSpinner.adapter = dropdownAdapter
    }

    override fun onResume() {
        super.onResume()

        setTables()

//        setUI()
    }

//    private fun setUI() {
//        binding.positionSpinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
//            override fun onNothingSelected(parent: AdapterView<*>?) {
//
//            }
//
//            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
//                val item = dropdownAdapter.getItem(position)
//                val number = item?.substring(item.indexOf(" ") + 1)
//                itemNumber = number?.toInt() ?: 1
//                setGuests(itemNumber, false)
//            }
//        }
//    }

    private fun setupObservers() {
        viewByTableViewModel.selectedItem.observe(viewLifecycleOwner, Observer {
            itemNumber = it
        })

//        viewByTableViewModel.guests.observe(viewLifecycleOwner, Observer {
//            var adapterAll : TableGuestsAdapter = TableGuestsAdapter(it, { guestItem : GuestsEntity -> guestItemClicked(guestItem) })
//            binding.guestsList.layoutManager = LinearLayoutManager(activity)
//            binding.guestsList.adapter = adapterAll
//            binding.guestsList.invalidate()
//        })
//
//        viewByTableViewModel.dropdown.observe(viewLifecycleOwner, Observer { list ->
//            val tableValues : List<String> = list.map { getString(R.string.table1 ) + " " + it }
//            dropdownAdapter = ArrayAdapter(requireContext(), R.layout.spinner_item, tableValues)
//            dropdownAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
//            binding.positionSpinner.adapter = dropdownAdapter
//        })
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
        viewByTableViewModel.getTables(getString(R.string.table1), false)
    }

    private fun setGuests(table : Int, forceUpdate : Boolean) {
        viewByTableViewModel.getPersonsFromTable(table, forceUpdate)
    }
}