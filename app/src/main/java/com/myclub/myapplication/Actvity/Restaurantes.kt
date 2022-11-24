package com.myclub.myapplication.Actvity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.myclub.myapplication.R
import com.myclub.myapplication.databinding.ActivityNotificacionesBinding
import com.myclub.myapplication.databinding.ActivityRestaurantesBinding

class Restaurantes : AppCompatActivity() {
    private lateinit var binding: ActivityRestaurantesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRestaurantesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        botones()    }
    private fun botones() {
        binding?.btnregresarres?.setOnClickListener {
            onBackPressed()
        }
    }

}