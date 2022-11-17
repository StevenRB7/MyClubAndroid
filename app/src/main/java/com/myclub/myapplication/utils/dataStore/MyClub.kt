package com.myclub.myapplication.utils.dataStore

import android.annotation.SuppressLint
import android.app.Application

class MyClub : Application() {
    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var sharedPreferences: MySharedPreferences
    }
    override fun onCreate() {
        super.onCreate()
        sharedPreferences = MySharedPreferences(applicationContext)
    }
}