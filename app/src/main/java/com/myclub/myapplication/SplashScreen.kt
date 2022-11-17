package com.myclub.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.myclub.myapplication.Actvity.IniciarSesion
import com.myclub.myapplication.databinding.ActivitySplashScreenBinding
import com.myclub.myapplication.utils.dataStore.MySharedPreferences

class SplashScreen : AppCompatActivity() {



    private lateinit var binding: ActivitySplashScreenBinding
    private lateinit var sharedPreferences: MySharedPreferences
    private lateinit var activeSessionUser: String

    private val timeSplash: Long = 2700

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        activeSessionUser = recoverInternalDataPreferences()

        splashScreen()
    }

    private fun splashScreen() {
        Handler().postDelayed(Runnable {
            if (activeSessionUser == "ActiveSession") {
                val intentSplash = Intent(this, MainActivity::class.java)
                startActivity(intentSplash)
                finish()
            } else {
                val intentSplash = Intent(this, IniciarSesion::class.java)
                startActivity(intentSplash)
                finish()
            }
        }, timeSplash)
    }

    private fun recoverInternalDataPreferences(): String {
        var sessionState = ""
        try {
            sharedPreferences = MySharedPreferences(this)
            sessionState = sharedPreferences.getActiveSessionUserPref()
        } catch (e: Exception) {
            //
        }
        return sessionState
    }
}