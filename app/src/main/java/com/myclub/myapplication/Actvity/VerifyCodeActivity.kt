package com.myclub.myapplication.Actvity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import com.myclub.myapplication.Actvity.AlertLoading.Companion.alertDialogLoading
import com.myclub.myapplication.R

import com.myclub.myapplication.dataDto.request.ConsultarCuentaRequestDto
import com.myclub.myapplication.dataDto.request.VerifyCoeRequestDto
import com.myclub.myapplication.databinding.ActivityVerifyCodeBinding
import com.myclub.myapplication.databinding.AlertLoadingBinding
import com.myclub.myapplication.network.ApiClient
import com.myclub.myapplication.network.ApiService
import com.myclub.myapplication.utils.Constantes
import com.myclub.myapplication.utils.Constantes.ERROR_FORMULARIO_VACIO
import com.myclub.myapplication.utils.Constantes.ID_PROYECTO
import com.myclub.myapplication.utils.alerts.AlertErrorResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class VerifyCodeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityVerifyCodeBinding
    private var idUserRetrieved: Double = 0.0
    private var userPerson: String = ""
    private var emailUser: String = ""
    private lateinit var query: ConsultarCuentaRequestDto
    private lateinit var verifyCoeRequestDto: VerifyCoeRequestDto
    //private lateinit var alertLoadingNew: AlertDialog


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVerifyCodeBinding.inflate(layoutInflater)
        setContentView(binding.root)


        getDataIntentExtras()

        binding.idBtnVerifyCode.setOnClickListener {
            if (validateTextFieldForm()) {
                val codeVerify = binding.idTxtUno.text.toString() +
                        binding.idTxtDos.text.toString() +
                        binding.idTxtTres.text.toString() +
                        binding.idTxtCuatro.text.toString()
                callCodeVerifyService(codeVerify, userPerson)
            }
        }
    }


    private fun callCodeVerifyService(codeVerify: String, login: String) {
        try {
            verifyCoeRequestDto = VerifyCoeRequestDto()
            verifyCoeRequestDto.idPersona = idUserRetrieved
            verifyCoeRequestDto.IdProyecto = ID_PROYECTO
            verifyCoeRequestDto.CodigoVerificacion = codeVerify
            verifyCoeRequestDto.Login = login

            val apiService: ApiService =
                ApiClient.RetrofitHelper(Constantes.BASE_URL_PERSONAS)
                    .create(ApiService::class.java)
            apiService.verifyCode(verifyCoeRequestDto)
                ?.enqueue(object : Callback<Boolean?> {
                override fun onResponse(
                    call: Call<Boolean?>, response: Response<Boolean?>) {
                    if (response.body() == true) {
                        AlertCheckEmail().alertCheckEmail(this@VerifyCodeActivity, "iniciar")
                    } else {
                        AlertErrorResponse().alertErrorResponseDialog(
                            this@VerifyCodeActivity,
                            Constantes.M_E_VERIFY_CODE)
                    }
                }

                override fun onFailure(call: Call<Boolean?>, t: Throwable) {

                }

            })
        } catch (e: Exception) {
        }
    }

    private fun validateTextFieldForm(): Boolean {
        var isValidForm = true
        try {
            if (binding.idTxtUno.text.toString().isEmpty()) {
                binding.idTxtUno.error = ERROR_FORMULARIO_VACIO
                isValidForm = false
            } else {
                binding.idTxtUno.error = null
                isValidForm = true
            }
            if (binding.idTxtDos.text.toString().isEmpty()) {
                binding.idTxtDos.error = ERROR_FORMULARIO_VACIO
                isValidForm = false
            } else {
                binding.idTxtDos.error = null
                isValidForm = true
            }
            if (binding.idTxtTres.text.toString().isEmpty()) {
                binding.idTxtTres.error = ERROR_FORMULARIO_VACIO
                isValidForm = false
            } else {
                binding.idTxtTres.error = null
                isValidForm = true
            }
            if (binding.idTxtCuatro.text.toString().isEmpty()) {
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

    private fun getDataIntentExtras() {
        try {
            idUserRetrieved = intent.extras?.get("IdPersona") as Double
            userPerson = intent.extras?.get("PhonePerson").toString()
            emailUser = intent.extras?.get("EmailUser").toString()
        } catch (e: Exception) {
            //
        }
    }

}
