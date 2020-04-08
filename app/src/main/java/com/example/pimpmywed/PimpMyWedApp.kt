package com.example.pimpmywed

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.example.pimpmywed.database.WedDatabase
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential

class PimpMyWedApp : Application() {

    companion object {
        lateinit var sharedPreferences : SharedPreferences
        var cacheKey : String = "value"
        var threshold : Long = 30

        var db : WedDatabase? = null

        lateinit var credentials : GoogleAccountCredential
    }

    override fun onCreate() {
        super.onCreate()

        sharedPreferences = getSharedPreferences("time", Context.MODE_PRIVATE)

        db = WedDatabase.getAppDataBase(context = applicationContext)
    }
}