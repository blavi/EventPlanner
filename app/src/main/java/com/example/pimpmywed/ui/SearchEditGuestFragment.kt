package com.example.pimpmywed.ui

import android.content.DialogInterface
import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pimpmywed.R
import com.example.pimpmywed.adapter.TableGuestsAdapter
import com.example.pimpmywed.database.GuestsEntity
import com.example.pimpmywed.databinding.FragmentSearchEditBinding
import com.example.pimpmywed.utils.*
import com.example.pimpmywed.viewmodel.SearchGuestsViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchEditGuestFragment : Fragment() {

    private val searchGuestsViewModel: SearchGuestsViewModel by viewModel()
    private lateinit var binding: FragmentSearchEditBinding

    private lateinit var editableQuery : Editable

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = FragmentSearchEditBinding.inflate(inflater, container, false)

        setupObservers()
        setUI()

        return binding.root
    }

    private fun setUI() {
        binding.searchEditTxtView.doAfterTextChanged {editable ->
            if (editable != null) {
                editableQuery = editable
            }
            sendQuery(editable, false)
        }
    }

    private fun sendQuery(editable: Editable?, forceUpdate : Boolean) {
        searchGuestsViewModel.getResult(editable, forceUpdate)
    }

    private fun updateList(list : List<GuestsEntity>) {
//        var adapterAll : TableGuestsAdapter = TableGuestsAdapter(list, { guestItem : GuestsEntity -> guestItemClicked(guestItem) })
//        binding.searchResult.layoutManager = LinearLayoutManager(requireActivity())
//        binding.searchResult.adapter = adapterAll
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
                sendQuery(editableQuery, true)

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

    private fun setupObservers() {
        searchGuestsViewModel.searchResult.observe(viewLifecycleOwner, Observer {
            when (it) {
                is ValidResult -> {
                    binding.otherResultTxtView.visibility = View.GONE
                    binding.searchResult.visibility = View.VISIBLE
                    updateList(it.result)
                }
                is ErrorResult -> {
                    updateList(emptyList())
                    binding.otherResultTxtView.visibility = View.VISIBLE
                    binding.searchResult.visibility = View.GONE
                    binding.otherResult = getString(R.string.search_error)
                }
                is EmptyResult -> {
                    updateList(emptyList())
                    binding.otherResultTxtView.visibility = View.VISIBLE
                    binding.searchResult.visibility = View.GONE
                    binding.otherResult = getString(R.string.empty_result)
                }
                is EmptyQuery -> {
                    updateList(emptyList())
                    binding.otherResultTxtView.visibility = View.VISIBLE
                    binding.searchResult.visibility = View.GONE
                    binding.otherResult = getString(R.string.not_enough_characters)
                }
                is TerminalError -> {
                    Toast.makeText(
                        activity,
                        getString(R.string.unexpected_error),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        })
    }
}