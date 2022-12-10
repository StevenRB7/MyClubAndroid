package com.myclub.myapplication.Actvity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.myclub.myapplication.R
import com.myclub.myapplication.dataDto.request.ConsultarRecuperarRequestDto
import com.myclub.myapplication.dataDto.request.SendCodeRequestDto
import com.myclub.myapplication.dataDto.response.ResponseDto
import com.myclub.myapplication.dataDto.utilsData.DataUtils
import com.myclub.myapplication.databinding.ActivityRecuperarContraBinding
import com.myclub.myapplication.databinding.AlertLoadingBinding
import com.myclub.myapplication.network.ApiClient
import com.myclub.myapplication.network.ApiService
import com.myclub.myapplication.utils.Constantes
import com.myclub.myapplication.utils.Constantes.*
import com.myclub.myapplication.utils.alerts.AlertErrorResponse
import com.myclub.myapplication.utils.alerts.AlertLoading
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.regex.Pattern

class RecuperarContraActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRecuperarContraBinding
    private lateinit var sendcodeRequestDto: SendCodeRequestDto
    private var recuperarResponseDto: ResponseDto? = null
    private lateinit var alertLoadingNew: AlertDialog
    private var dataUtils: DataUtils = DataUtils()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        binding = ActivityRecuperarContraBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dataUtils = DataUtils()
        alertLoadingShow()
        BotonesDeRecuperar()
        recuperarResponseDto = ResponseDto()

    }

    private fun BotonesDeRecuperar() {

        binding.idtxtregistrarse.setOnClickListener {
            val i = Intent(this, Registro::class.java)
            startActivity(i)
            finish()
        }
        binding.idBtnRecuperarPasword.setOnClickListener{

            if (validateTextFields()) {
                callService()
            }
        }

    }
    private fun validateTextFields(): Boolean {
        var esValido = true
        try {
            if (binding.idTxtUserName.text.toString().isEmpty()) {
                binding.idTxtUserName.error = ERROR_FORMULARIO_VACIO
                esValido = false
            } else {
                binding.idTxtUserName.error = null
                esValido = true
            }

            if (binding.idTxtUserEmail.text.toString().isEmpty()) {
                binding.idTxtUserEmail.error = ERROR_FORMULARIO_VACIO
                esValido = false
            } else {
                esValido = true
                binding.idTxtUserEmail.error = null
            }

        } catch (e: Exception) {
            //
        }
        return esValido
    }
    private fun callService() {
        try {
            alertLoadingNew.show()
            sendcodeRequestDto = SendCodeRequestDto()
            sendcodeRequestDto.IdProject =ID_PROYECTO
            sendcodeRequestDto.Phone = binding.idTxtUserName.text.toString()

            val apiService: ApiService =
                ApiClient.RetrofitHelper(BASE_URL_PERSONAS).create(ApiService::class.java)
            apiService.ContraVeriCode(sendcodeRequestDto)
                ?.enqueue(object : Callback<ResponseDto?>{
                    override fun onResponse(
                        call: Call<ResponseDto?>,
                        response: Response<ResponseDto?>
                    ) {
                        alertLoadingNew.dismiss()
                        if (response.body() != null) {
                            recuperarResponseDto = response.body()!!
                            accionesDeRespuesta(recuperarResponseDto)

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

        when (codeResponse!!.CodeResponse) {
            CodeSuccess -> {
                val i =
                    Intent(this, VerifyCodeActivity::class.java)
                i.putExtra("IdPerson", recuperarResponseDto?.Data!!.IdPerson.toString())
                i.putExtra("Login", binding.idTxtUserName.text.toString())
                i.putExtra("Email", binding.idTxtUserEmail.text.toString())
                i.putExtra("recuperar", "YesIsRecover")
                startActivity(i)
            }
            CodeInvalidArgument -> {
                AlertErrorResponse().alertErrorResponseDialog(
                    this@RecuperarContraActivity, MessageInvalidRequest, dataUtils
                )
                AlertErrorResponse.alertDialogErrorResponse.show()
            }
            CodeElementAlreadyExists -> {
                AlertErrorResponse().alertErrorResponseDialog(
                    this@RecuperarContraActivity, MessageElementAlreadyExists, dataUtils
                )
                AlertErrorResponse.alertDialogErrorResponse.show()
            }
            CodeServer -> {
                AlertErrorResponse().alertErrorResponseDialog(
                    this@RecuperarContraActivity, MessageErrorServer, dataUtils
                )
                AlertErrorResponse.alertDialogErrorResponse.show()
            }
        }
    }

    private fun validateEmail(email: String): Boolean {
        val pattern: Pattern = Patterns.EMAIL_ADDRESS
        pattern.matcher(email).matches()
        return pattern.matcher(email).matches()
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