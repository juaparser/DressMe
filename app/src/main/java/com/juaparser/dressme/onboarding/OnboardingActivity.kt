package com.juaparser.dressme.onboarding

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager.widget.ViewPager
import com.juaparser.dressme.MainActivity
import com.juaparser.dressme.R

/*
*
* Actividad para mostrar el tutorial
*
 */

class OnboardingActivity : AppCompatActivity() {
    lateinit var viewPager: ViewPager
    lateinit var adapter: SlideViewPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding)
        viewPager = findViewById(R.id.viewpager)
        adapter = SlideViewPagerAdapter(this,viewPager)

        viewPager.adapter = adapter
        if (isOpenAlread()) {
            val intent = Intent(this@OnboardingActivity, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
        } else {
            val editor = getSharedPreferences("slide", MODE_PRIVATE).edit()
            editor.putBoolean("slide", true)
            editor.commit()
        }
    }

    private fun isOpenAlread(): Boolean {
        val sharedPreferences = getSharedPreferences("slide", MODE_PRIVATE)
        return sharedPreferences.getBoolean("slide", false)
    }


}