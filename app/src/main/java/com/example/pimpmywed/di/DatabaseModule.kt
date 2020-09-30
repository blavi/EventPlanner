package com.example.pimpmywed.di

import android.app.Application
import androidx.room.Room
import com.example.pimpmywed.database.GuestsDAO
import com.example.pimpmywed.database.WedDatabase
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val databaseModule = module {

    fun provideDatabase(application: Application): WedDatabase {
        return Room.databaseBuilder(application, WedDatabase::class.java, "guests")
            .fallbackToDestructiveMigration()
            .build()
    }

    fun providePersonsDao(database: WedDatabase): GuestsDAO {
        return  database.guestsDAO()
    }

    single { provideDatabase(androidApplication()) }
    single { providePersonsDao(get()) }
}