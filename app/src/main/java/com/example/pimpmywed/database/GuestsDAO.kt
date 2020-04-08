package com.example.pimpmywed.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface GuestsDAO {

    @Query("SELECT * FROM Guests")
    fun getGuests(): List<GuestsEntity>

//    @Query("SELECT name FROM Guests GROUP BY table")
//    fun getSelectedGuests(menuVal : String): List<GuestsEntity>

    @Insert
    fun insertAll(guests: List<GuestsEntity>)

    @Query("DELETE FROM Guests")
    fun removeAll()
}