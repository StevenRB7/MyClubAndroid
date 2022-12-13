package com.myclub.myapplication.Actvity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.myclub.myapplication.R
import com.myclub.myapplication.dataDto.request.CambiarContraRequestDto
import com.myclub.myapplication.dataDto.response.ResponseDto
import com.myclub.myapplication.dataDto.utilsData.DataUtils
import com.myclub.myapplication.databinding.ActivityCambiarContrasenaBinding
import com.myclub.myapplication.databinding.AlertLoadingBinding
import com.myclub.myapplication.network.ApiClient
import com.myclub.myapplication.network.ApiService
import com.myclub.myapplication.utils.Constantes
import com.myclub.myapplication.utils.Constantes.*
import com.myclub.myapplication.utils.alerts.AlertCheckCambiar
import com.myclub.myapplication.utils.alerts.AlertErrorResponse
import com.myclub.myapplication.utils.dataStore.MyClub.Companion.sharedPreferences
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CambiarContrasenaActivity : AppCompatActivity() {


    private lateinit var binding: ActivityCambiarContrasenaBinding
    private var cambiarRequestDto: CambiarContraRequestDto? = null
    private var cambiarResponseDto: ResponseDto? = null
    private lateinit var alertLoadingNew: AlertDialog
    private var dataUtils: DataUtils = DataUtils()
    private var login: String = ""
    private var idPerson: String = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCambiarContrasenaBinding.inflate(layoutInflater)
        setContentView(binding.root)

botones()
        cambiarResponseDto = ResponseDto()
        alertLoadingShow()

    }

    private fun botones() {
        binding.btnCambiarContrasena.setOnClickListener {
            Toast.makeText(this, "En proceso Servicio en Mantenimiento",Toast.LENGTH_SHORT).show()
            if (ValidacionesCambiarContra()) {
                callService()

            }
        }
    }


    private fun callService() {
        try {
            alertLoadingNew.show()
            cambiarRequestDto = CambiarContraRequestDto()
            cambiarRequestDto!!.IdProject = ID_PROYECTO
            cambiarRequestDto!!.IdPerson = idPerson
            cambiarRequestDto!!.Login =  login
            cambiarRequestDto!!.Password = binding.idnuevacontra.text.toString()

            val apiService: ApiService =
                ApiClient.RetrofitHelper(BASE_URL_PERSONAS).create(ApiService::class.java)
            apiService.CambiarContra(cambiarRequestDto)
                .enqueue(object : Callback<ResponseDto?> {
                    override fun onResponse(
                        call: Call<ResponseDto?>,
                        response: Response<ResponseDto?>
                    ) {
                        alertLoadingNew.dismiss()

                        if (response.body() != null) {
                            cambiarResponseDto = response.body()!!
                            accionesDeRespuesta(cambiarResponseDto)

                        }
                    }

                    override fun onFailure(call: Call<ResponseDto?>, t: Throwable) {
                        alertLoadingNew.dismiss()
                    }

                })

        } catch (e: Exception){
            alertLoadingNew.dismiss()

        }
    }

    private fun accionesDeRespuesta(codeResponse: ResponseDto?) {
        //Toast.makeText(this, "${codeResponse!!}", Toast.LENGTH_SHORT).show()
        dataUtils = DataUtils()
        when (codeResponse!!.CodeResponse) {
            CodeSuccess -> {
                sharedPreferences.storeIdUser(cambiarResponseDto!!.Data!!.IdPerson.toString())
                sharedPreferences.storeActiveSessionUser(PREF_ACTIVE_SESSION)
                AlertCheckCambiar().alertCheckCambiar(
                    this@CambiarContrasenaActivity,
                    "iniciarSesion"
                )

            }
            CodeInvalidArgument -> {
                AlertErrorResponse().alertErrorResponseDialog(
                    this@CambiarContrasenaActivity, MessageInvalidRequest, dataUtils
                )
                AlertErrorResponse.alertDialogErrorResponse.show()
            }
            CodeServer -> {
                AlertErrorResponse().alertErrorResponseDialog(
                    this@CambiarContrasenaActivity, MessageErrorServer, dataUtils
                )
                AlertErrorResponse.alertDialogErrorResponse.show()
            }
        }
    }

    private fun ValidacionesCambiarContra(): Boolean {
        var isValidForm = true
        try {
            if (binding.idnuevacontra.text.toString().isNotEmpty()) {
                isValidForm = true
                binding.idnuevacontra.error = null
            } else {
                isValidForm = false
                binding.idnuevacontra.error = ERROR_FORMULARIO_VACIO
            }

        } catch (e: Exception) {
            //
        }
        return isValidForm;
    }
    //ALERTA GLOBAL CARGANDO
    private fun alertLoadingShow() {
        try {
            val viewAlert = AlertLoadingBinding.inflate(layoutInflater)
            alertLoadingNew = AlertDialog.Builder(this).apply {
                setView(viewAlert.root)
                setCancelable(false)
            }.create()
            viewAlert.idTxtxMessage.text = "Cargando Datos"
            alertLoadingNew.window?.setBackgroundDrawableResource(R.color.transparente)
        } catch (e: Exception) {
            //
        }
    }
}