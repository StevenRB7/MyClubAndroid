package com.myclub.myapplication.ui.home

import android.os.Bundle
import android.view.View

import androidx.fragment.app.Fragment

import com.myclub.myapplication.R
import com.myclub.myapplication.adapter.Carrusel
import com.myclub.myapplication.adapter.CarruselAdapter
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


        //binding?.idbtnrestaurantes?.setOnClickListener {
           // val i = Intent(requireContext(), Restaurantes::class.java)
            //startActivity(i)
        //}
        val carrusel = ArrayList<Carrusel>()
        carrusel.add(Carrusel(R.drawable.cardbanner))
        carrusel.add(Carrusel(R.drawable.membresiaplus))
        carrusel.add(Carrusel(R.drawable.membresiamyq))
        carrusel.add(Carrusel(R.drawable.cardbanner))
        carrusel.add(Carrusel(R.drawable.membresiamyq))

        val adapter = CarruselAdapter(carrusel)

        binding?.apply {
            carouselRecyclerview.adapter = adapter
            carouselRecyclerview.setAlpha(false)
            carouselRecyclerview.setInfinite(true)
            carouselRecyclerview.setIsScrollingEnabled(true)
            carouselRecyclerview.set3DItem(true)

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