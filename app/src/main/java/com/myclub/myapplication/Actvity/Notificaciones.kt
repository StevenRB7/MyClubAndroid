package com.myclub.myapplication.Actvity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.myclub.myapplication.R
import com.myclub.myapplication.databinding.ActivityIniciarSesionBinding
import com.myclub.myapplication.databinding.ActivityNotificacionesBinding

class Notificaciones : AppCompatActivity() {

    private lateinit var binding: ActivityNotificacionesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNotificacionesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        botones()
    }

    private fun botones() {
        binding?.btnregresarnoti?.setOnClickListener {
            onBackPressed()
        }
    }
}