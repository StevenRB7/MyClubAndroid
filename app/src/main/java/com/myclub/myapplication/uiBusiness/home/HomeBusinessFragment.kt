package com.myclub.myapplication.uiBusiness.home

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.myclub.myapplication.Actvity.Belleza_Deporte
import com.myclub.myapplication.Actvity.Hoteles_Resorts
import com.myclub.myapplication.Actvity.Restaurantes
import com.myclub.myapplication.Actvity.Veterinaria
import com.myclub.myapplication.R
import com.myclub.myapplication.databinding.FragmentHomeBinding
import com.myclub.myapplication.databinding.FragmentHomeBusinessBinding
import com.myclub.myapplication.databinding.FragmentPromocionesBinding
import com.myclub.myapplication.ui.promociones.PromocionesViewModel
import com.myclub.myapplication.utils.dataStore.MyClub
import com.myclub.myapplication.utils.dataStore.MySharedPreferences

class HomeBusinessFragment : Fragment(R.layout.fragment_home_business) {

    private var binding: FragmentHomeBusinessBinding? = null
    private var idPersonRecovered = ""


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBusinessBinding.bind(view)
    }
    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }



}