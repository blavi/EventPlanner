package com.example.pimpmywed.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [GuestsEntity::class], version = 1)
abstract class WedDatabase : RoomDatabase() {
    abstract fun guestsDAO(): GuestsDAO

    companion object {
        var INSTANCE: WedDatabase? = null

        fun getAppDataBase(context: Context): WedDatabase? {
            if (INSTANCE == null){
                synchronized(WedDatabase::class){
                    INSTANCE = Room.databaseBuilder(context.applicationContext, WedDatabase::class.java, "myDB").build()
                }
            }
            return INSTANCE
        }
    }
}