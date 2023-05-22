package com.guillecarrasco.practicafinal.app

import android.app.Application
import com.google.firebase.FirebaseApp

class PracticaFinalApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
    }

}