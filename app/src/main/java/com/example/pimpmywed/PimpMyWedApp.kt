package com.example.pimpmywed

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.example.pimpmywed.di.*
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidFileProperties
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class PimpMyWedApp : Application() {

    companion object {
        lateinit var sharedPreferences : SharedPreferences
        var cacheKey : String = "value"
        var threshold : Long = 30

        lateinit var credentials : GoogleAccountCredential
    }

    override fun onCreate() {
        super.onCreate()

        sharedPreferences = getSharedPreferences("time", Context.MODE_PRIVATE)

        startKoin {
            // use AndroidLogger as Koin Logger - default Level.INFO
            androidLogger(Level.ERROR)

            // use the Android context given there
            androidContext(this@PimpMyWedApp)

            // load properties from assets/koin.properties file
            androidFileProperties()

            // module list
            modules(listOf(viewModelModule, networkModule, databaseModule, apiModule, repositoryModule))
        }
    }
}