package com.myclub.myapplication.uiNavBusiness.perfilBusiness

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.google.zxing.integration.android.IntentIntegrator
import com.myclub.myapplication.R
import com.myclub.myapplication.dataDto.request.CanjearQRRequestDto
import com.myclub.myapplication.dataDto.request.generadorQRDto
import com.myclub.myapplication.dataDto.response.CanjearQRResponseDto
import com.myclub.myapplication.databinding.FragmentPerfilBusinnesBinding
import com.myclub.myapplication.network.ApiClient
import com.myclub.myapplication.network.ApiService
import com.myclub.myapplication.utils.Constantes
import com.myclub.myapplication.utils.dataStore.MyClub
import com.myclub.myapplication.utils.dataStore.MySharedPreferences
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Exception
import java.util.Objects

class PerfilBusinnesFragment : Fragment(R.layout.fragment_perfil_businnes) {

    private var binding: FragmentPerfilBusinnesBinding? = null

    private lateinit var canjearResponse: CanjearQRResponseDto
    private lateinit var canjearRequest: CanjearQRRequestDto


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentPerfilBusinnesBinding.bind(view)


        binding?.BtnEscanearQR?.setOnClickListener { initScanner() }


    }

    private fun callCajearService() {

        try {
            canjearRequest = CanjearQRRequestDto()
            canjearRequest.IdPersonShop = recoverIdPersonShared().toString().toDouble()
            canjearRequest.IdProject = Constantes.ID_PROYECTO
            canjearRequest.IdCoupon = 0.0
            canjearRequest.IdPlan = 0.0


            val apiService: ApiService =
                ApiClient.RetrofitHelper(Constantes.BASE_MY_CLUB).create(ApiService::class.java)

            apiService.CanjearQR(canjearRequest)
                .enqueue(object : Callback<CanjearQRResponseDto?> {
                    override fun onResponse(
                        call: Call<CanjearQRResponseDto?>,
                        response: Response<CanjearQRResponseDto?>
                    ) {
                        canjearResponse = response.body()!!
                        if (canjearResponse.Codigo == 500) {

                        }

                    }

                    override fun onFailure(call: Call<CanjearQRResponseDto?>, t: Throwable) {
                    }

                })

        } catch (e: Exception) {
            //
        }
    }


    private fun initScanner() {
        val integrator = IntentIntegrator.forSupportFragment(this@PerfilBusinnesFragment)
        integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES)
        integrator.setPrompt("Escanear código QR para redimir vaucher");
        integrator.setTorchEnabled(false)
        integrator.setBeepEnabled(true)
        integrator.initiateScan()
        integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE);
        callCajearService()

    }


    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result != null) {
            if (result.contents == null) {
                Toast.makeText(context, "Cancelado", Toast.LENGTH_SHORT).show()
            } else {
                var newww = "'holaa:'IdPersonShop'"
                Log.e("Resultado", result.contents)
                for (i in result.contents) {
                    if (i.toString() == "'") {
                        newww = i.toString().replace("'","""""").toString()
                        Log.e("Resultado=>", newww)
                    }
                }

            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    private fun recoverIdPersonShared(): String {
        var idPerson = ""
        try {
            MyClub.sharedPreferences = MySharedPreferences(requireContext())
            idPerson = MyClub.sharedPreferences.recoverIdPersonPref()
        } catch (e: Exception) {
            //
        }
        return idPerson
    }

}