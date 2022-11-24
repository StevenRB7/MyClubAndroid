package com.myclub.myapplication.uiNavBusiness.perfilBusiness

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import com.google.zxing.integration.android.IntentIntegrator
import com.myclub.myapplication.R
import com.myclub.myapplication.databinding.FragmentPerfilBusinnesBinding
import com.myclub.myapplication.utils.DataUserInsert
import java.lang.Exception

class PerfilBusinnesFragment : Fragment(R.layout.fragment_perfil_businnes) {

    private var binding: FragmentPerfilBusinnesBinding? = null
    private var dataUserInsert: DataUserInsert? = null



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentPerfilBusinnesBinding.bind(view)


        binding?.BtnEscanearQR?.setOnClickListener { initScanner() }

    }


    private fun initScanner() {
        val integrator = IntentIntegrator.forSupportFragment(this@PerfilBusinnesFragment)
        integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES)
        integrator.setPrompt("Escanear código QR para redimir vaucher");
        integrator.setTorchEnabled(false)
        integrator.setBeepEnabled(true)
        integrator.initiateScan()
        integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE);

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result != null) {
            if (result.contents == null) {
                Toast.makeText(context, "Cancelado", Toast.LENGTH_SHORT).show()
            } else {
                Log.e("Resultado", result.formatName)
                Toast.makeText(context, "Resultado=> ${result.contents}", Toast.LENGTH_LONG).show()
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

}