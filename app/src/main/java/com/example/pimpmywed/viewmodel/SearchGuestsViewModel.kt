package com.example.pimpmywed.viewmodel

import android.text.Editable
import androidx.annotation.VisibleForTesting
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.pimpmywed.repository.PersonsRepository
import com.example.pimpmywed.utils.*
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.mapLatest

class SearchGuestsViewModel : ViewModel() {
    private var forceQueryUpdate: Boolean = false
    companion object {
        const val SEARCH_DELAY_MS = 500L
        const val MIN_QUERY_LENGTH = 3
    }

    fun getResult(editable : Editable?, forceUpdate : Boolean) {
        forceQueryUpdate = forceUpdate
        viewModelScope.launch {
            queryChannel.send(editable.toString())
        }
    }

    @ExperimentalCoroutinesApi
    @VisibleForTesting
    internal val queryChannel = BroadcastChannel<String>(Channel.CONFLATED)

    @FlowPreview
    @ExperimentalCoroutinesApi
    @VisibleForTesting
    internal val internalSearchResult = queryChannel
        .asFlow()
        .debounce(SEARCH_DELAY_MS)
        .mapLatest {
            try {
                if (it.length >= MIN_QUERY_LENGTH) {
                    val searchResult = withContext(Dispatchers.IO) {
                        PersonsRepository.getInstance().getGuestsBasedOnQueryAsync(forceQueryUpdate, it).await()
                    }

                    if (searchResult.isNotEmpty()) {
                        ValidResult(searchResult)
                    } else {
                        EmptyResult
                    }
                } else {
                    EmptyQuery
                }
            } catch (e: Throwable) {
                if (e is CancellationException) {
                    println("Search was cancelled!")
                    throw e
                } else {
                    ErrorResult(e)
                }
            }
        }
        .catch { it: Throwable -> emit(TerminalError) }

    @FlowPreview
    @ExperimentalCoroutinesApi
    val searchResult = internalSearchResult.asLiveData()

//    class Factory() :
//        ViewModelProvider.NewInstanceFactory() {
//        @Suppress("UNCHECKED_CAST")
//        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
//            return SearchGuestsViewModel() as T
//        }
//    }
}