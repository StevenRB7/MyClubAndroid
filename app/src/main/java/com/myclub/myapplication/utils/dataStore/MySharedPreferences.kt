package com.myclub.myapplication.utils.dataStore

import android.content.Context
import com.myclub.myapplication.utils.Constantes.*

class MySharedPreferences(private val context: Context) {
        //MANTENDER LA SESION ACTIVA PASANDOLE EL PARAMETRO A SPLASH
    val myStorePreference = context.getSharedPreferences(PREF_NAME, 0)

    /**
     * FUNCTIONS STORE PREFERENCES
     * */
    fun storeIdUser(idUser: String) {
        myStorePreference.edit().putString(PREF_ID_USUARIO, idUser).apply()
    }
    fun storeIdRol(idRol: String) {
        myStorePreference.edit().putString(PREF_ID_ROL, idRol).apply()
    }


    fun storeActiveSessionUser(active: String) {
        myStorePreference.edit().putString(PREF_ACTIVE_SESSION, active).apply()
    }


    /**
     * FUNCTIONS READ PREFERENCES
     * */
    fun getActiveSessionUserPref(): String {return myStorePreference.getString(PREF_ACTIVE_SESSION, "IdleSessionUser")!!}


    fun recoverIdPersonPref(): String {return myStorePreference.getString(PREF_ID_USUARIO, "IdPersonEmpty")!!}

    fun restoreIdRoleUserPref(): String {return myStorePreference.getString(PREF_ID_ROL, "IdRolEmpty")!!}

    fun deleteMySharedPreferences() {
        myStorePreference.edit().clear().apply()
    }
}