package com.guillecarrasco.practicafinal.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.guillecarrasco.practicafinal.R

class LoginActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        auth = Firebase.auth
    }

    override fun onStart() {
        super.onStart()
        auth.currentUser?.let {
            findNavController(R.id.navHostFragment).navigate(R.id.action_loginFragment_to_productsActivity)
        }
    }
    override fun onBackPressed() {
        findNavController(R.id.navHostFragment).popBackStack()
    }
}