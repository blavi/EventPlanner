package com.example.pimpmywed.utils

import com.example.pimpmywed.database.GuestsEntity

sealed class SearchResult
class ValidResult(val result: List<GuestsEntity>) : SearchResult()
object EmptyResult : SearchResult()
object EmptyQuery : SearchResult()
class ErrorResult(val e: Throwable) : SearchResult()
object TerminalError : SearchResult()