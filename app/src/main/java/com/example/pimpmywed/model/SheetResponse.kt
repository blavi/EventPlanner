package com.example.pimpmywed.model

import com.beust.klaxon.Json

data class SheetResponse (
    @Json(name = "range")
    val range: String,

    @Json(name = "majorDimension")
    val majorDimension: String,

    @Json(name = "values")
    val values: List<List<String>>
)