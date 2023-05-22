package com.guillecarrasco.practicafinal.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.guillecarrasco.practicafinal.R

class MainActivity : AppCompatActivity() {

    private lateinit var drawer: DrawerLayout
    private lateinit var auth: FirebaseAuth
    private val navController by lazy {
        Navigation.findNavController(this, R.id.navHostFragmentProducts)
    }
    private lateinit var navigationView: NavigationView
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_products)
        auth = Firebase.auth
        setSupportActionBar(findViewById(R.id.toolbar))
        navigationView = findViewById(R.id.navigationView)
        drawerLayout = findViewById(R.id.drawerLayout)
        toolbar = findViewById(R.id.toolbar)
        setNav()

//        setListeners()
    }

    private fun setNav() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.navHostFragmentProducts) as NavHostFragment
        val navController = navHostFragment.navController
        navigationView.setupWithNavController(navController)
        navigationView.menu.findItem(R.id.logout).setOnMenuItemClickListener { _ ->
            Firebase.auth.signOut()
            finish()
            true
        }
        NavigationUI.setupWithNavController(toolbar, navController, drawerLayout)
    }

    override fun onBackPressed() {
        findNavController(R.id.navHostFragmentProducts).popBackStack()
    }
}