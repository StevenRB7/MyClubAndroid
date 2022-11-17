package com.myclub.myapplication.uiNav.configuracion

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ConfiguracionViewModel : ViewModel() {
    private val _text = MutableLiveData<String>().apply {
        value = "This is dashboard Fragment"
    }
}