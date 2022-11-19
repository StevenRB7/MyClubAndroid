package com.myclub.myapplication.Actvity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.myclub.myapplication.R
import com.myclub.myapplication.databinding.ActivityRecuperarContraBinding

class RecuperarContra : AppCompatActivity() {
    private lateinit var binding: ActivityRecuperarContraBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        binding = ActivityRecuperarContraBinding.inflate(layoutInflater)
        setContentView(binding.root)
        BotonesDeRecuperar()

    }

    private fun BotonesDeRecuperar() {
        binding.idtxtregistrarse.setOnClickListener {
            val i = Intent(this, Registro::class.java)
            startActivity(i)
            finish()
        }
    }
}