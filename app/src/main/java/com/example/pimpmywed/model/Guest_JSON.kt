package com.example.pimpmywed.model

import com.beust.klaxon.Json

data class Guest_JSON (
    @Json(name = "Nume")
    val nume: String,

    @Json(name = "Invitat_NUMAR")
    val invitat_numar: Long,

    @Json(name = "Invitat")
    val invitat: String,

    @Json(name = "Confirmat")
    val confirmat: Long?,

    @Json(name = "Meniu")
    val meniu: String,

    @Json(name = "Varsta")
    val varsta: Long
)