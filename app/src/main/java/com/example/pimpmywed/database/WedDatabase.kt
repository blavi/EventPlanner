package com.example.pimpmywed.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [GuestsEntity::class], version = 1)
abstract class WedDatabase : RoomDatabase() {
    abstract fun guestsDAO(): GuestsDAO
}