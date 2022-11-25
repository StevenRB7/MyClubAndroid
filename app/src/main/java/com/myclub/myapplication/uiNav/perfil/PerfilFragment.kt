package com.myclub.myapplication.uiNav.perfil


import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.zxing.integration.android.IntentIntegrator
import com.myclub.myapplication.Actvity.AlertErrorResponse
import com.myclub.myapplication.Actvity.AlertLoading
import com.myclub.myapplication.Actvity.VerifyCodeActivity
import com.myclub.myapplication.R
import com.myclub.myapplication.dataDto.PersonalModelDto
import com.myclub.myapplication.dataDto.response.ResponseDto
import com.myclub.myapplication.databinding.FragmentPerfilBinding
import com.myclub.myapplication.network.ApiClient
import com.myclub.myapplication.network.ApiService
import com.myclub.myapplication.utils.Constantes
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PerfilFragment : Fragment(R.layout.fragment_perfil) {

    private lateinit var personaRequest: PersonalModelDto
    private lateinit var responseDto: ResponseDto

    private var binding: FragmentPerfilBinding? = null


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentPerfilBinding.bind(view)

        callUserperfilService()
        //binding?.BtnEscanearQR?.setOnClickListener { initScanner() }

    }


    private fun initScanner() {
        val integrator = IntentIntegrator.forSupportFragment(this@PerfilFragment)
        integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES)
        integrator.setPrompt("Escanear código QR para redimir vaucher");
        integrator.setTorchEnabled(false)
        integrator.setBeepEnabled(true)
        integrator.initiateScan()
        integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE);

    }
    private fun callUserperfilService() {
        try {
            setData()
            responseDto = ResponseDto()
            AlertLoading.alertDialogLoading.show()
            val apiService: ApiService =
                ApiClient.RetrofitHelper(Constantes.BASE_URL_PERSONAS)
                    .create(ApiService::class.java)
            apiService.registerNewUser(personaRequest)?.enqueue(object : Callback<ResponseDto?> {
                override fun onResponse(
                    call: Call<ResponseDto?>,
                    response: Response<ResponseDto?>
                ) {
                    responseDto = response.body()!!


                }

                override fun onFailure(call: Call<ResponseDto?>, t: Throwable) {
                }
            })
        } catch (e: Exception) {
        }
    }
    private fun setData() {
        try {
            personaRequest = PersonalModelDto()
            personaRequest.PrimerNombre = binding?.txtnombreperfil?.text.toString()
            personaRequest.SegundoNombre = ""
            personaRequest.PrimerApellidos = ""
            personaRequest.SegundoApellido = ""
            personaRequest.Sexo = ""
            personaRequest.FechaNacimiento = ""
            personaRequest.TipoDocumento = 1.0
            personaRequest.Documento = binding?.txtcedula?.text.toString()
            personaRequest.EstadoCivil = ""
            personaRequest.Telefono = ""
            personaRequest.Celular = binding?.txtcelular?.text.toString()
            personaRequest.Direccion = ""
            personaRequest.Correo = binding?.txtcorreoelectronico?.text.toString()
            personaRequest.IdProyecto = Constantes.ID_PROYECTO
            personaRequest.IdRol = Constantes.ID_ROL_PERSONA_NATURAL
        } catch (e: Exception) {
        }
    }




}



