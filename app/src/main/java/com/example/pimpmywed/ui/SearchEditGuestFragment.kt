package com.example.pimpmywed.ui

import android.app.Activity
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pimpmywed.adapter.TableGuestsAdapter
import com.example.pimpmywed.database.GuestsEntity
import com.example.pimpmywed.databinding.FragmentSearchEditBinding
import com.example.pimpmywed.utils.Constants
import com.example.pimpmywed.viewmodel.SearchGuestsViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel


class SearchEditGuestFragment : Fragment() {

    private val searchGuestsViewModel: SearchGuestsViewModel by viewModel()
    private lateinit var binding: FragmentSearchEditBinding

    private lateinit var editableQuery : String

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = FragmentSearchEditBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = searchGuestsViewModel

        setupObservers()
        setUI()

        return binding.root
    }

    private fun setUI() {
//        binding.searchEditTxtView.doAfterTextChanged {editable ->
//            if (editable != null) {
//                editableQuery = editable
//            }
//            sendQuery(editable, false)
//        }

        var adapterAll : TableGuestsAdapter = TableGuestsAdapter { guestItem: GuestsEntity -> guestItemClicked(
            guestItem
        ) }
        binding.searchResult.apply {
            layoutManager = LinearLayoutManager(requireActivity())
            adapter = adapterAll
        }
    }

    private fun sendQuery(editable: String, forceUpdate: Boolean) {
        searchGuestsViewModel.getResult(editable, forceUpdate)
    }

//    private fun updateList(list : List<GuestsEntity>) {
//        var adapterAll : TableGuestsAdapter = TableGuestsAdapter { guestItem : GuestsEntity -> guestItemClicked(guestItem) }
//        binding.searchResult.apply {
//            layoutManager = LinearLayoutManager(requireActivity())
//            adapter = adapterAll
//        }
//    }

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
        searchGuestsViewModel.query.observe(viewLifecycleOwner, Observer {
            editableQuery = it
            showLoadingDialog()
        })

        searchGuestsViewModel.searchResult.observe(viewLifecycleOwner, Observer {
            hideLoadingDialog()
            val inputMethodManager = requireActivity().getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(requireActivity().currentFocus!!.windowToken, 0)
        })

//        searchGuestsViewModel.searchResult.observe(viewLifecycleOwner, Observer {
//            when (it) {
//                is ValidResult -> {
//                    binding.otherResultTxtView.visibility = View.GONE
//                    binding.searchResult.visibility = View.VISIBLE
//                    updateList(it.result)
//                }
//                is ErrorResult -> {
//                    updateList(emptyList())
//                    binding.otherResultTxtView.visibility = View.VISIBLE
//                    binding.searchResult.visibility = View.GONE
//                    binding.otherResult = getString(R.string.search_error)
//                }
//                is EmptyResult -> {
//                    updateList(emptyList())
//                    binding.otherResultTxtView.visibility = View.VISIBLE
//                    binding.searchResult.visibility = View.GONE
//                    binding.otherResult = getString(R.string.empty_result)
//                }
//                is EmptyQuery -> {
//                    updateList(emptyList())
//                    binding.otherResultTxtView.visibility = View.VISIBLE
//                    binding.searchResult.visibility = View.GONE
//                    binding.otherResult = getString(R.string.not_enough_characters)
//                }
//                is TerminalError -> {
//                    Toast.makeText(
//                        activity,
//                        getString(R.string.unexpected_error),
//                        Toast.LENGTH_SHORT
//                    ).show()
//                }
//            }
//        })
    }
}