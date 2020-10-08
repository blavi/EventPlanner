package com.example.pimpmywed.utils

import com.example.pimpmywed.R
import com.example.pimpmywed.database.GuestsEntity

sealed class SearchResult {
    abstract fun isValid(): Boolean
    abstract fun getMessage(): Int?
}

class ValidResult(val result: List<GuestsEntity>) : SearchResult(){
    override fun isValid(): Boolean {
        return true
    }

    override fun getMessage(): Int? {
        return null
    }
}

object EmptyResult : SearchResult() {
    override fun isValid(): Boolean {
        return false
    }

    override fun getMessage(): Int {
        return R.string.empty_result
    }
}

object EmptyQuery : SearchResult() {
    override fun isValid(): Boolean {
        return false
    }

    override fun getMessage(): Int {
        return R.string.not_enough_characters
    }
}

class ErrorResult(val e: Throwable) : SearchResult() {
    override fun isValid(): Boolean {
        return false
    }

    override fun getMessage(): Int {
        return R.string.search_error
    }
}

object TerminalError : SearchResult() {
    override fun isValid(): Boolean {
        return false
    }

    override fun getMessage(): Int {
        return R.string.unexpected_error
    }
}