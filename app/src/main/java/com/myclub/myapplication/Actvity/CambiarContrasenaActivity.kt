package com.myclub.myapplication.Actvity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.myclub.myapplication.databinding.ActivityCambiarContrasenaBinding
import com.myclub.myapplication.utils.Constantes

class CambiarContrasenaActivity : AppCompatActivity() {


    private lateinit var binding: ActivityCambiarContrasenaBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCambiarContrasenaBinding.inflate(layoutInflater)
        setContentView(binding.root)
        botones()
    }

    private fun botones() {
        binding.btnCambiarContrasena.setOnClickListener {
        if (ValidacionesCambiarContra()) {

    }
}
    }

    private fun ValidacionesCambiarContra(): Boolean {
        var isValidForm = true
        try {
            if (binding.idnuevacontra.text.toString().isNotEmpty()) {
                isValidForm = true
                binding.idnuevacontra.error = null
            } else {
                isValidForm = false
                binding.idnuevacontra.error = Constantes.ERROR_FORMULARIO_VACIO
            }

            if (binding.idconfirmarcontra.text.toString().isNotEmpty()) {
                isValidForm = true
                binding.idconfirmarcontra.error = null
            } else {
                isValidForm = false
                binding.idconfirmarcontra.error = Constantes.ERROR_FORMULARIO_VACIO
            }
        } catch (e: Exception) {
            //
        }
        return isValidForm;
    }
}