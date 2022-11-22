package com.myclub.myapplication.uiNav.perfil


import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.core.view.MenuItemCompat
import androidx.fragment.app.Fragment
import com.google.zxing.integration.android.IntentIntegrator
import com.myclub.myapplication.R
import com.myclub.myapplication.databinding.FragmentPerfilBinding

class PerfilFragment : Fragment(R.layout.fragment_perfil) {

    private var binding: FragmentPerfilBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentPerfilBinding.bind(view)


        //when(binding?.BtnEscanearQR?.id?.toDouble()){
        //191.0->{
        binding?.BtnEscanearQR?.setOnClickListener { initScanner() }
        //View.VISIBLE
        //}
        //157.0->{
        //View.INVISIBLE
        //}
    }


    private fun initScanner() {
        val integrator = IntentIntegrator.forSupportFragment(this@PerfilFragment)
        integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES)
        integrator.setPrompt("Escanear código QR para redimir cupones");
        integrator.setTorchEnabled(false)
        integrator.setBeepEnabled(true)
        integrator.initiateScan()
        integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE);

    }

}



