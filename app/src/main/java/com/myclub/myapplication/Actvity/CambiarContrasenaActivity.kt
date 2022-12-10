package com.myclub.myapplication.Actvity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
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
import com.myclub.myapplication.utils.alerts.AlertErrorResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.regex.Pattern

class CambiarContrasenaActivity : AppCompatActivity() {


    private lateinit var binding: ActivityCambiarContrasenaBinding
    private var cambiarRequestDto: CambiarContraRequestDto? = null
    private var cambiarResponseDto: ResponseDto? = null
    private lateinit var alertLoadingNew: AlertDialog
    private var dataUtils: DataUtils = DataUtils()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCambiarContrasenaBinding.inflate(layoutInflater)
        setContentView(binding.root)


        cambiarResponseDto = ResponseDto()
        botones()
        alertLoadingShow()

    }

    private fun botones() {
        binding.btnCambiarContrasena.setOnClickListener {
            if (ValidacionesCambiarContra()) {
                binding.idnuevacontra.text.toString()
                    callService()
            }
        }
    }

    private fun callService() {
        try {
            alertLoadingNew.show()
            cambiarRequestDto = CambiarContraRequestDto()
            cambiarRequestDto!!.IdProject = Constantes.ID_PROYECTO
            cambiarRequestDto!!.IdPerson = ""
            cambiarRequestDto!!.Login =  ""
            cambiarRequestDto!!.Password = binding.idnuevacontra.text.toString()

            val apiService: ApiService =
                ApiClient.RetrofitHelper(Constantes.BASE_URL_PERSONAS).create(ApiService::class.java)
            apiService.CambiarContra(cambiarRequestDto)
                .enqueue(object : Callback<ResponseDto?> {
                    override fun onResponse(
                        call: Call<ResponseDto?>,
                        response: Response<ResponseDto?>
                    ) {
                        Log.e("hhh",response.body()!!.CodeResponse.toString() )
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
        Toast.makeText(this, "${codeResponse!!.MessageResponse}", Toast.LENGTH_SHORT).show()
        when (codeResponse.CodeResponse) {
            Constantes.CodeSuccess -> {
                val i =
                    Intent(this@CambiarContrasenaActivity, IniciarSesion::class.java)

                startActivity(i)
            }
            Constantes.CodeInvalidArgument -> {
                AlertErrorResponse().alertErrorResponseDialog(
                    this@CambiarContrasenaActivity, Constantes.MessageInvalidRequest, dataUtils
                )
                AlertErrorResponse.alertDialogErrorResponse.show()
            }
            Constantes.CodeElementAlreadyExists -> {
                AlertErrorResponse().alertErrorResponseDialog(
                    this@CambiarContrasenaActivity, Constantes.MessageElementAlreadyExists, dataUtils
                )
                AlertErrorResponse.alertDialogErrorResponse.show()
            }
            Constantes.CodeServer -> {
                AlertErrorResponse().alertErrorResponseDialog(
                    this@CambiarContrasenaActivity, Constantes.MessageErrorServer, dataUtils
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
                binding.idnuevacontra.error = Constantes.ERROR_FORMULARIO_VACIO
            }

            if (binding.idconfirmarcontra.text.toString().isNotEmpty()) {
                isValidForm = true
                binding.idconfirmarcontra.error = null
            } else {
                isValidForm = false
                binding.idconfirmarcontra.error = Constantes.ERROR_FORMULARIO_VACIO
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
            viewAlert.idTxtxMessage.text = "Validando Datos"
            alertLoadingNew.window?.setBackgroundDrawableResource(R.color.transparente)
        } catch (e: Exception) {
            //
        }
    }
}