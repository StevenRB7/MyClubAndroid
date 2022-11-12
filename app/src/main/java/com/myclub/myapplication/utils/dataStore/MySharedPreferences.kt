package com.myclub.myapplication.utils.dataStore

import android.content.Context
import com.myclub.myapplication.utils.Constantes.*

class MySharedPreferences(private val context: Context) {

    val myStorePreference = context.getSharedPreferences(PREF_NAME, 0)

    fun storeIdUser(idUser: String) {
        myStorePreference.edit().putString(PREF_ID_USUARIO, idUser).apply()
    }

    fun storeActiveSessionUser(active: String) {
        myStorePreference.edit().putString(PREF_ACTIVE_SESSION, active).apply()
    }
}