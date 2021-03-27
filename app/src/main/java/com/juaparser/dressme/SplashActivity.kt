package com.juaparser.dressme

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.juaparser.dressme.onboarding.OnboardingActivity

/*
    Actividad para mostrar la pantalla inicial o SplashScreen
 */

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val intent = Intent(this, OnboardingActivity::class.java) // Navegar a onboarding
        startActivity(intent)
        finish()
    }
}