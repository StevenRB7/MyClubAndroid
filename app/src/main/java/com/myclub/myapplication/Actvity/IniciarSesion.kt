package com.myclub.myapplication.Actvity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import com.myclub.myapplication.MainActivity
import com.myclub.myapplication.MainActivityBusiness
import com.myclub.myapplication.R
import com.myclub.myapplication.dataDto.request.SignInRequestDto
import com.myclub.myapplication.dataDto.response.ResponseDto
import com.myclub.myapplication.dataDto.utilsData.DataUtils
import com.myclub.myapplication.databinding.ActivityIniciarSesionBinding
import com.myclub.myapplication.databinding.AlertLoadingBinding
import com.myclub.myapplication.network.ApiClient
import com.myclub.myapplication.network.ApiService
import com.myclub.myapplication.utils.Constantes
import com.myclub.myapplication.utils.Constantes.*
import com.myclub.myapplication.utils.alerts.AlertErrorResponse
import com.myclub.myapplication.utils.alerts.AlertErrorResponse.Companion.alertDialogErrorResponse
import com.myclub.myapplication.utils.alerts.AlertLoading
import com.myclub.myapplication.utils.alerts.AlertLoading.Companion.alertDialogLoading
import com.myclub.myapplication.utils.dataStore.MySharedPreferences
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class IniciarSesion : Activity() {

    private lateinit var binding: ActivityIniciarSesionBinding
    private var signInRequestdDto: SignInRequestDto? = null
    private var queryPersonByIdResponseDto: ResponseDto? = null
    private lateinit var sharedPreferences: MySharedPreferences
    private lateinit var alertLoadingNew: AlertDialog
    private lateinit var dataUtils: DataUtils



    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityIniciarSesionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        dataUtils = DataUtils()

        alertLoadingShow()
        queryPersonByIdResponseDto = ResponseDto()

        AlertLoading().alertLoadingDialog(this, M_VALIDATE_DATA)
        AlertErrorResponse().alertErrorResponseDialog(this, M_ERROR_VALIDATE_DATA, dataUtils)

        botones()

    }

    private fun botones() {
        binding.idBtnIngresar.setOnClickListener {
            if (ValidacionesLogin()) {
                iniciarsesionService(binding.idusuario.text.toString(), binding.idpwd.text.toString())
            }
        }
        binding.idBtnRegistro.setOnClickListener {
            val i = Intent(this, Registro::class.java)
            startActivity(i)
        }
        binding.idrecuperarContrasena.setOnClickListener {
            val inte = Intent(this, RecuperarContraActivity::class.java)
            startActivity(inte)
        }

    }

    private fun ValidacionesLogin(): Boolean {
        var isValidForm = true
        try {
            if (binding.idusuario.text.toString().isNotEmpty()) {
                isValidForm = true
                binding.idusuario.error = null
            } else {
                isValidForm = false
                binding.idusuario.error = Constantes.ERROR_FORMULARIO_VACIO
            }

            if (binding.idpwd.text.toString().isNotEmpty()) {
                isValidForm = true
                binding.idpwd.error = null
            } else {
                isValidForm = false
                binding.idpwd.error = Constantes.ERROR_FORMULARIO_VACIO
            }
        } catch (e: Exception) {
            //
        }
        return isValidForm;
    }


    private fun iniciarsesionService(User: String, password: String) {

        alertLoadingNew.show()

        signInRequestdDto = SignInRequestDto()
        signInRequestdDto!!.UserName = User
        signInRequestdDto!!.Password = password
        signInRequestdDto!!.IdProject = ID_PROYECTO.toString()


        val apiService: ApiService =
            ApiClient.RetrofitHelper(BASE_URL_PERSONAS).create(ApiService::class.java)
        apiService.signInUser(signInRequestdDto)
            ?.enqueue(object : Callback<ResponseDto?>{
                override fun onResponse(
                    call: Call<ResponseDto?>,
                    response: Response<ResponseDto?>
                ) {
                    alertLoadingNew.dismiss()
                    if (response.body() != null) {
                        queryPersonByIdResponseDto = response.body()
                        sharedPreferences = MySharedPreferences(this@IniciarSesion)

                        accionesDeRespuesta(queryPersonByIdResponseDto)

                    }

                }

                override fun onFailure(call: Call<ResponseDto?>, t: Throwable) {
                    alertLoadingNew.dismiss()
                }

            })
    }
    private fun accionesDeRespuesta(responseDto: ResponseDto?) {

        when (responseDto!!.CodeResponse) {
            CodeSuccess -> {
                sharedPreferences.storeIdUser(queryPersonByIdResponseDto!!.Data!!.IdPerson.toString())
                sharedPreferences.storeActiveSessionUser("ActiveSession")
                alertDialogLoading.dismiss()
                jumpToViewDesition(queryPersonByIdResponseDto?.Data?.IdRoleUser.toString())

            }
            CodeNoFoundElement -> {
                val i = Intent(this, Registro::class.java)
                startActivity(i)
            }
            CodeUnauthorizedAccess -> {
                dataUtils = DataUtils()
                dataUtils.isVerify = "veriricar"
                dataUtils.IdPerson = responseDto.Data!!.IdPerson
                dataUtils.Phone = responseDto.Data!!.Phone
                AlertErrorResponse().alertErrorResponseDialog(
                    this, MessageUnauthorizedAccess, dataUtils
                )
                alertDialogErrorResponse.show()
            }

            CodeInvalidArgument -> {
                AlertErrorResponse().alertErrorResponseDialog(
                    this, MessageInvalidRequest, dataUtils
                )
                alertDialogErrorResponse.show()
            }
            CodePasswordInvalid -> {
                AlertErrorResponse().alertErrorResponseDialog(
                    this, MessagePasswordInvalid, dataUtils
                )
                alertDialogErrorResponse.show()
            }
            CodeServer -> {
                AlertErrorResponse().alertErrorResponseDialog(
                    this, MessageErrorServer, dataUtils
                )
                alertDialogErrorResponse.show()
            }
        }
    }


    private fun jumpToViewDesition(DireccionDeRoles: String) {
        try {
            val i: Intent
            when (DireccionDeRoles) {
                "1" -> {
                    i = Intent(this@IniciarSesion, MainActivity::class.java)
                    sharedPreferences.storeIdUser(queryPersonByIdResponseDto?.Data?.IdPerson.toString())
                    sharedPreferences.storeActiveSessionUser("ActiveSession")
                    sharedPreferences.storeIdRol(queryPersonByIdResponseDto?.Data?.IdRoleUser.toString())
                    startActivity(i)
                    finish()
                }
                "2" -> {
                    i = Intent(this@IniciarSesion, MainActivityBusiness::class.java)
                    sharedPreferences.storeIdRol(queryPersonByIdResponseDto?.Data?.IdRoleUser.toString())
                    sharedPreferences.storeActiveSessionUser("ActiveSession")
                    startActivity(i)
                    finish()
                }
            }
        } catch (e: Exception) {
            //
        }
    }

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