package com.myclub.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        Thread.sleep(1500)

        setTheme(R.style.Theme_MyClub)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

    }
}