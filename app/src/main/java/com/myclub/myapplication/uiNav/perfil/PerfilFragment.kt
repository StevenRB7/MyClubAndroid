package com.myclub.myapplication.uiNav.perfil


import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.myclub.myapplication.Actvity.EscanearQRAdminActivity
import com.myclub.myapplication.Actvity.Restaurantes
import com.myclub.myapplication.R
import com.myclub.myapplication.databinding.FragmentHomeBinding
import com.myclub.myapplication.databinding.FragmentPerfilBinding

class PerfilFragment : Fragment(R.layout.fragment_perfil) {

    private var binding: FragmentPerfilBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentPerfilBinding.bind(view)

        //prueba escanear
        binding?.BtnEscanearQR?.setOnClickListener {
            val i = Intent(requireContext(), EscanearQRAdminActivity::class.java)
            startActivity(i)
        }
    }

}

