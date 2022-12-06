@file:Suppress("SENSELESS_COMPARISON")

package com.myclub.myapplication.uiNavBusiness.perfilBusiness

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.gson.Gson
import com.google.zxing.integration.android.IntentIntegrator
import com.myclub.myapplication.Actvity.AlertLoading
import com.myclub.myapplication.Actvity.IniciarSesion
import com.myclub.myapplication.R
import com.myclub.myapplication.dataDto.request.RedimirCuponUsuarioDto
import com.myclub.myapplication.dataDto.response.CanjearQRResponseDto
import com.myclub.myapplication.databinding.AlertConfirmarCompraBinding
import com.myclub.myapplication.databinding.AlertLoadingBinding
import com.myclub.myapplication.databinding.FragmentPerfilBusinnesBinding
import com.myclub.myapplication.network.ApiClient
import com.myclub.myapplication.network.ApiService
import com.myclub.myapplication.utils.Constantes
import com.myclub.myapplication.utils.alerts.AlertConfirmarCompra
import com.myclub.myapplication.utils.dataStore.MySharedPreferences
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.nio.file.attribute.AclEntry.Builder

class PerfilBusinnesFragment : Fragment(R.layout.fragment_perfil_businnes) {

    private var binding: FragmentPerfilBusinnesBinding? = null

    private lateinit var canjearResponse: CanjearQRResponseDto
    private lateinit var canjearRequest: RedimirCuponUsuarioDto
    private lateinit var myAlertDialogOpcion: AlertDialog


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentPerfilBusinnesBinding.bind(view)
        AlertLoading().alertLoadingDialog(requireContext(), "Validando")


        botones()
        binding?.BtnEscanearQR?.setOnClickListener { initScanner() }


    }

    private fun botones() {
        binding?.btncerrarsesionbusiness?.setOnClickListener {
            MySharedPreferences(requireContext()).deleteMySharedPreferences()
            val i = Intent(requireContext(), IniciarSesion::class.java)
            startActivity(i)
        }
    }

    private fun callCajearService(
        IdPersonShop: Double,
        IdCoupon: Double,
        IdTrade: Double,
        IdUserAssociated: Double
    ) {

        try {
            canjearRequest = RedimirCuponUsuarioDto()
            canjearRequest.IdPersonTrade = IdPersonShop
            canjearRequest.IdProject = Constantes.ID_PROYECTO.toDouble()
            canjearRequest.IdCoupon = IdCoupon
            canjearRequest.IdTrade = IdTrade
            canjearRequest.IdUserAssociated = IdUserAssociated


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
                                    "Este codigo ya esta canjeado",
                                    Toast.LENGTH_SHORT
                                ).show()
                            } else {

                                myAlertDialogOpcion.dismiss()
                                Toast.makeText(
                                    requireContext(),
                                    "¡Codigo canjeado correctamente!",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }

                    override fun onFailure(call: Call<CanjearQRResponseDto?>, t: Throwable) {
                    }
                })

        } catch (e: Exception) {
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


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result != null) {
            if (result.contents == null) {
                Toast.makeText(context, "Cancelado", Toast.LENGTH_SHORT).show()
            } else {
                val gson = Gson()
                val codeResult = gson.fromJson(result.contents, RedimirCuponUsuarioDto::class.java)


                val viewAlert = AlertConfirmarCompraBinding.inflate(layoutInflater)
                val alertBuilder = AlertDialog.Builder(requireContext()).apply {
                    setView(viewAlert.root)
                }.create()
                try {
                    alertDialogOpcionView(
                        codeResult.IdPersonTrade!!.toDouble(),
                        codeResult.IdCoupon!!.toDouble(),
                        codeResult.IdTrade!!.toDouble(),
                        codeResult.IdUserAssociated!!.toDouble()
                    )


                } catch (_: Exception) {

                }
            }
        }
    }

    /**
     * UTILEDADES
     */

    private fun alertDialogOpcionView(
        IdPersonTrade: Double,
        IdCoupon: Double,
        IdTrade: Double,
        IdUserAssociated: Double
    ) {
        try {
            val viewAlert = AlertConfirmarCompraBinding.inflate(layoutInflater)
            myAlertDialogOpcion = AlertDialog.Builder(requireContext()).apply {
                setView(viewAlert.root)
                setCancelable(false)
            }.create()
            viewAlert.idBtnComfirmarCompra.setOnClickListener {
                callCajearService(
                    IdPersonTrade,
                    IdCoupon,
                    IdTrade,
                    IdUserAssociated
                )
            }
            viewAlert.idBtnCancelarCompra.setOnClickListener {
                Toast.makeText(requireContext(), "cancelado", Toast.LENGTH_SHORT).show()
                myAlertDialogOpcion.dismiss()
            }

//            viewAlert.idTxtxMessage.text = "Cargando membresias"
            //myAlertDialogOpcion.window?.setBackgroundDrawableResource(R.color.transparent)
            myAlertDialogOpcion.show()
        } catch (e: Exception) {
            //
        }
    }


}



