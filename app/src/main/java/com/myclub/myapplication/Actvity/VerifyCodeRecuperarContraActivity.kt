package com.myclub.myapplication.Actvity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import com.myclub.myapplication.R
import com.myclub.myapplication.dataDto.request.VerificarcodigoRequestDto
import com.myclub.myapplication.dataDto.response.ResponseDto
import com.myclub.myapplication.dataDto.utilsData.DataUtils
import com.myclub.myapplication.databinding.ActivityVerifyCodeBinding
import com.myclub.myapplication.databinding.AlertLoadingBinding
import com.myclub.myapplication.network.ApiClient
import com.myclub.myapplication.network.ApiService
import com.myclub.myapplication.utils.Constantes.*
import com.myclub.myapplication.utils.alerts.AlertCheckRecuperar
import com.myclub.myapplication.utils.alerts.AlertErrorResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class VerifyCodeRecuperarContraActivity : AppCompatActivity() {

    private lateinit var binding: ActivityVerifyCodeBinding
    private var login: String = ""
    private var idPerson: String = ""
    private var email: String = ""
    private var recuperarcontra: Boolean = false
    private var isRecover: String = ""
    private lateinit var verifyCoeRequestDto: VerificarcodigoRequestDto
    private lateinit var responseDto: ResponseDto
    private lateinit var alertLoadingNew: AlertDialog
    private var dataUtils: DataUtils = DataUtils()




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVerifyCodeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        alertLoadingShow()
        getDataIntentExtras()

        binding.idBtnVerifyCode.setOnClickListener {
            if (validateTextFieldForm()) {
                val codeVerify = binding.idTxtUno.text.toString() +
                        binding.idTxtDos.text.toString() +
                        binding.idTxtTres.text.toString() +
                        binding.idTxtCuatro.text.toString()
                callCodeVerifyService(codeVerify)
            }
        }
    }


    private fun callCodeVerifyService(codeVerify: String) {

        try {
            alertLoadingNew.show()

            verifyCoeRequestDto = VerificarcodigoRequestDto()
            verifyCoeRequestDto.IdPerson = idPerson
            verifyCoeRequestDto.Login = login
            verifyCoeRequestDto.Email = email
            verifyCoeRequestDto.IdProject = ID_PROYECTO
            verifyCoeRequestDto.CodeVerification = codeVerify
            verifyCoeRequestDto.IsRecoverPassword = recuperarcontra


            val apiService: ApiService =
                ApiClient.RetrofitHelper(BASE_URL_PERSONAS)
                    .create(ApiService::class.java)
            apiService.CambiarContraCode(verifyCoeRequestDto)
                ?.enqueue(object : Callback<ResponseDto?>{
                    override fun onResponse(
                        call: Call<ResponseDto?>,
                        response: Response<ResponseDto?>
                    ) {
                        alertLoadingNew.dismiss()

                        if (response.body() != null) {
                            responseDto = response.body()!!
                            Accionesderespuesta(responseDto)

                        }
                    }

                    override fun onFailure(call: Call<ResponseDto?>, t: Throwable) {
                        alertLoadingNew.dismiss()

                    }

                })
        } catch (e: Exception) {
            alertLoadingNew.dismiss()

        }
    }

    //acciones de alertas con diferentes codigos de respuesta
    private fun Accionesderespuesta(responseDto: ResponseDto?) {
        dataUtils = DataUtils()
        when (responseDto!!.CodeResponse) {
            CodeSuccess -> {

                AlertCheckRecuperar().alertCheckRecuperar(
                    this@VerifyCodeRecuperarContraActivity,
                    "verificarRecuperar"
                )
            }

            CodeVerificationCodeInvalid -> {
                AlertErrorResponse().alertErrorResponseDialog(
                    this, MessageVerifyCodeInvalid, dataUtils
                )
                AlertErrorResponse.alertDialogErrorResponse.show()
            }
            CodeServer -> {
                AlertErrorResponse().alertErrorResponseDialog(
                    this, MessageErrorServer, dataUtils
                )
                AlertErrorResponse.alertDialogErrorResponse.show()
            }
        }
    }

    //validaciones de verificar codigo

    private fun validateTextFieldForm(): Boolean {
        var isValidForm = true
        try {
            if (binding.idTxtUno.text.toString().isNullOrEmpty()) {
                binding.idTxtUno.error = ERROR_FORMULARIO_VACIO
                isValidForm = false
            } else {
                binding.idTxtUno.error = null
                isValidForm = true
            }
            if (binding.idTxtDos.text.toString().isNullOrEmpty()) {
                binding.idTxtDos.error = ERROR_FORMULARIO_VACIO
                isValidForm = false
            } else {
                binding.idTxtDos.error = null
                isValidForm = true
            }
            if (binding.idTxtTres.text.toString().isNullOrEmpty()) {
                binding.idTxtTres.error = ERROR_FORMULARIO_VACIO
                isValidForm = false
            } else {
                binding.idTxtTres.error = null
                isValidForm = true
            }
            if (binding.idTxtCuatro.text.toString().isNullOrEmpty()) {
                binding.idTxtCuatro.error = ERROR_FORMULARIO_VACIO
                isValidForm = false
            } else {
                binding.idTxtCuatro.error = null
                isValidForm = true
            }
        } catch (e: Exception) {
            isValidForm = false
        }
        return isValidForm
    }

    //alerta cargando global
    private fun alertLoadingShow() {
        try {
            val viewAlert = AlertLoadingBinding.inflate(layoutInflater)
            alertLoadingNew = AlertDialog.Builder(this).apply {
                setView(viewAlert.root)
                setCancelable(false)
            }.create()
            viewAlert.idTxtxMessage.text = "Validando sus datos por favor espere"
            alertLoadingNew.window?.setBackgroundDrawableResource(R.color.transparente)
        } catch (e: Exception) {
            //
        }
    }

    //Acciones de putextra que recibe desde Recuperar contraseña
    private fun getDataIntentExtras() {
        try {
            idPerson = intent.extras?.get("IdPerson").toString()
            login = intent.extras?.get("Login").toString()
            email = intent.extras?.get("Email").toString()
            isRecover = intent.extras?.get("isRecover").toString()


            if (isRecover == "YesIsRecover") {
                recuperarcontra = true
            }
        } catch (e: Exception) {
            //
        }
    }


}
