package com.example.pimpmywed.ui

import android.content.DialogInterface
import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pimpmywed.R
import com.example.pimpmywed.adapter.TableGuestsAdapter
import com.example.pimpmywed.database.GuestsEntity
import com.example.pimpmywed.utils.*
import com.example.pimpmywed.viewmodel.SearchGuestsViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.concurrent.fixedRateTimer

class SearchEditGuestFragment : Fragment() {

    private lateinit var searchGuestsViewModel: SearchGuestsViewModel
    private lateinit var guestsList : RecyclerView
    private lateinit var editTextView : EditText
    private lateinit var otherResult : TextView
    private lateinit var editableQuery : Editable
    private lateinit var progressBar : ProgressBar

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        searchGuestsViewModel = ViewModelProvider(this).get(SearchGuestsViewModel::class.java)

        val root = inflater.inflate(R.layout.fragment_search_edit, container, false)
        guestsList= root.findViewById(R.id.searchResult)
        editTextView = root.findViewById(R.id.searchEditTxtView)
        otherResult = root.findViewById(R.id.otherResultTxtView)
        progressBar = root.findViewById(R.id.progressbar)

        setupObservers()
        setUI()

        return root
    }

    private fun setUI() {
        editTextView.doAfterTextChanged {editable ->
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
        var adapterAll : TableGuestsAdapter = TableGuestsAdapter(list, { guestItem : GuestsEntity -> guestItemClicked(guestItem) })
        guestsList.layoutManager = LinearLayoutManager(activity)
        guestsList.adapter = adapterAll
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
                sendQuery(editableQuery, true)

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

    private fun setupObservers() {
        searchGuestsViewModel.searchResult.observe(viewLifecycleOwner, Observer {
            when (it) {
                is ValidResult -> {
                    otherResult.visibility = View.GONE
                    guestsList.visibility = View.VISIBLE
                    updateList(it.result)
                }
                is ErrorResult -> {
                    updateList(emptyList())
                    otherResult.visibility = View.VISIBLE
                    guestsList.visibility = View.GONE
                    otherResult.setText(R.string.search_error)
                }
                is EmptyResult -> {
                    updateList(emptyList())
                    otherResult.visibility = View.VISIBLE
                    guestsList.visibility = View.GONE
                    otherResult.setText(R.string.empty_result)
                }
                is EmptyQuery -> {
                    updateList(emptyList())
                    otherResult.visibility = View.VISIBLE
                    guestsList.visibility = View.GONE
                    otherResult.setText(R.string.not_enough_characters)
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