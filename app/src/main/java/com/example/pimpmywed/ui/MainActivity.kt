package com.example.pimpmywed.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.pimpmywed.PimpMyWedApp
import com.example.pimpmywed.R
import com.example.pimpmywed.databinding.ActivityMainBinding
import com.example.pimpmywed.databinding.FragmentDashboardBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.Scope
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential
import com.google.api.services.sheets.v4.SheetsScopes
import com.jakewharton.threetenabp.AndroidThreeTen
import np.com.susanthapa.curved_bottom_navigation.CbnMenuItem
import np.com.susanthapa.curved_bottom_navigation.CurvedBottomNavigationView

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    companion object {
        private const val REQUEST_SIGN_IN = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this

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

        setBottomNavigationView(navController)

        requestSignIn()
    }

    private fun setBottomNavigationView(navController: NavController) {
        val menuItems = arrayOf(
            CbnMenuItem(
                R.drawable.ic_timer, // the icon
                R.drawable.ic_avd_timer, // the AVD that will be shown in FAB
                R.id.navigation_home // optional if you use Jetpack Navigation
            ),
            CbnMenuItem(
                R.drawable.ic_bar_chart_black,
                R.drawable.ic_avd_bar_chart,
                R.id.navigation_dashboard
            ),
            CbnMenuItem(
                R.drawable.ic_all_out_black,
                R.drawable.ic_avd_all_out,
                R.id.navigation_notifications
            ),
            CbnMenuItem(
                R.drawable.ic_search,
                R.drawable.ic_avd_search,
                R.id.navigation_search_edit
            )
        )
        binding.navView.setMenuItems(menuItems, 0)

        binding.navView.setupWithNavController(navController)
    }

    private fun requestSignIn() {

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
