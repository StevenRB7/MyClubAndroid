package com.myclub.myapplication.uiNavBusiness.perfilBusiness

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import com.google.gson.Gson
import com.google.zxing.integration.android.IntentIntegrator
import com.myclub.myapplication.Actvity.AlertCheckEmail
import com.myclub.myapplication.Actvity.AlertConfirmarCompra
import com.myclub.myapplication.R
import com.myclub.myapplication.dataDto.request.RedimirCuponUsuarioDto
import com.myclub.myapplication.dataDto.response.CanjearQRResponseDto
import com.myclub.myapplication.databinding.AlertConfirmarCompraBinding
import com.myclub.myapplication.databinding.FragmentPerfilBusinnesBinding
import com.myclub.myapplication.network.ApiClient
import com.myclub.myapplication.network.ApiService
import com.myclub.myapplication.utils.Constantes
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception

class PerfilBusinnesFragment : Fragment(R.layout.fragment_perfil_businnes) {

    private var binding: FragmentPerfilBusinnesBinding? = null

    private lateinit var canjearResponse: CanjearQRResponseDto
    private lateinit var canjearRequest: RedimirCuponUsuarioDto
    private lateinit var codeResult: RedimirCuponUsuarioDto


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentPerfilBusinnesBinding.bind(view)


        binding?.BtnEscanearQR?.setOnClickListener { initScanner() }


    }

    private fun callCajearService(IdCoupon: Double, IdPlan: Double, idPerson: Double) {

        try {
            canjearRequest = RedimirCuponUsuarioDto()
            canjearRequest.IdPersonShop = idPerson
            canjearRequest.IdProject = Constantes.ID_PROYECTO
            canjearRequest.IdCoupon = IdCoupon
            canjearRequest.IdShop = IdPlan


            val apiService: ApiService =
                ApiClient.RetrofitHelper(Constantes.BASE_MY_CLUB).create(ApiService::class.java)

            apiService.CanjearQR(canjearRequest)
                .enqueue(object : Callback<CanjearQRResponseDto?> {
                    override fun onResponse(
                        call: Call<CanjearQRResponseDto?>, response: Response<CanjearQRResponseDto?>
                    ) {

                        if (response.body() != null) {
                            canjearResponse = response.body()!!

                            if (canjearResponse.Codigo == 500) {
                                Toast.makeText(
                                    requireContext(),
                                    "${canjearResponse.Codigo}",
                                    Toast.LENGTH_SHORT
                                ).show()
                            } else {
                                Toast.makeText(
                                    requireContext(),
                                    "${canjearResponse.Codigo}",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }


                    }

                    override fun onFailure(call: Call<CanjearQRResponseDto?>, t: Throwable) {
                    }

                })

        } catch (e: Exception) {
            Log.e("Errrr", e.message.toString())
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

    }


    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result != null) {
            if (result.contents == null) {
                Toast.makeText(context, "Cancelado", Toast.LENGTH_SHORT).show()
            } else {
                val gson = Gson()
                val codeResult = gson.fromJson(result.contents, RedimirCuponUsuarioDto::class.java)
                val viewAlert = AlertConfirmarCompraBinding.inflate(layoutInflater)
                val alertBuild = AlertDialog.Builder(requireContext()).apply {
                    setView(viewAlert.root)
                }.create()
                viewAlert.idBtnComfirmarCompra.setOnClickListener {
                    callCajearService(

                        codeResult.IdCoupon!!.toDouble(),
                        codeResult.IdShop!!.toDouble(),
                        codeResult.IdPersonShop!!.toDouble()

                    )
                    AlertConfirmarCompra().alertConfirmarCompra(requireContext(), "comprar")


                }
                alertBuild.show()

            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)

        }
    }
}



