package com.example.pimpmywed.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.pimpmywed.PimpMyWedApp
import com.example.pimpmywed.R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.Scope
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential
import com.google.api.services.sheets.v4.SheetsScopes
import com.jakewharton.threetenabp.AndroidThreeTen

class MainActivity : AppCompatActivity() {
    companion object {
        private const val REQUEST_SIGN_IN = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        AndroidThreeTen.init(this)

        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home,
                R.id.navigation_dashboard,
                R.id.navigation_notifications,
                R.id.navigation_search_edit
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        requestSignIn()
    }

    private fun requestSignIn() {
        /*
        GoogleSignIn.getLastSignedInAccount(context)?.also { account ->
            Timber.d("account=${account.displayName}")
        }
         */

        val signInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
             .requestEmail()
            // .requestScopes(Scope(SheetsScopes.SPREADSHEETS_READONLY))
            .requestScopes(Scope(SheetsScopes.SPREADSHEETS))
            .build()
        val client = GoogleSignIn.getClient(this, signInOptions)

        startActivityForResult(client.signInIntent, REQUEST_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_SIGN_IN) {
            if (resultCode == RESULT_OK) {
                GoogleSignIn.getSignedInAccountFromIntent(data)
                    .addOnSuccessListener { account ->
                        val scopes = listOf(SheetsScopes.SPREADSHEETS)
                        PimpMyWedApp.credentials = GoogleAccountCredential.usingOAuth2(this, scopes)
                        PimpMyWedApp.credentials.selectedAccount = account.account
                    }
                    .addOnFailureListener { e ->
                    }
            }
        }
    }
}
