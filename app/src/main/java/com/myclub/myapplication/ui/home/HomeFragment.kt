package com.myclub.myapplication.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.View

import androidx.fragment.app.Fragment
import com.myclub.myapplication.Actvity.*

import com.myclub.myapplication.R
import com.myclub.myapplication.databinding.FragmentHomeBinding
import com.myclub.myapplication.utils.dataStore.MyClub.Companion.sharedPreferences
import com.myclub.myapplication.utils.dataStore.MySharedPreferences

class HomeFragment : Fragment(R.layout.fragment_home) {

    private var binding: FragmentHomeBinding? = null
    private var idPersonRecovered = ""


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view)

        idPersonRecovered = recoverIdPersonShared()


        binding?.idbtnrestaurantes?.setOnClickListener {
            val i = Intent(requireContext(), Restaurantes::class.java)
            startActivity(i)
        }
        binding?.idBtnhoteles?.setOnClickListener {
            val i = Intent(requireContext(), Hoteles_Resorts::class.java)
            startActivity(i)
        }
        binding?.idBtnbellezaydeporte?.setOnClickListener {
            val i = Intent(requireContext(), Belleza_Deporte::class.java)
            startActivity(i)
        }
        binding?.idBtnveterinaria?.setOnClickListener {
            val i = Intent(requireContext(), Veterinaria::class.java)
            startActivity(i)
        }
    }



    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
    private fun recoverIdPersonShared(): String {
        var idPerson = ""
        try {
            sharedPreferences = MySharedPreferences(requireContext())
            idPerson = sharedPreferences.recoverIdPersonPref()
        } catch (e: Exception) {
            //
        }
        return idPerson
    }


}