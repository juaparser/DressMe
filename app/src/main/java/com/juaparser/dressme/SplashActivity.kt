package com.juaparser.dressme

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

/*
    Actividad para mostrar la pantalla inicial o SplashScreen
 */

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val intent = Intent(this, MainActivity::class.java) // Navegar a onboarding
        startActivity(intent)
        finish()
    }
}