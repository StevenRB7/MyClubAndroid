package com.myclub.myapplication.Actvity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.myclub.myapplication.Actvity.AlertErrorResponse.Companion.alertDialogErrorResponse
import com.myclub.myapplication.Actvity.AlertLoading.Companion.alertDialogLoading
import com.myclub.myapplication.MainActivity
import com.myclub.myapplication.MainActivityBusiness
import com.myclub.myapplication.dataDto.PersonalModelDto
import com.myclub.myapplication.dataDto.request.SignInRequestDto
import com.myclub.myapplication.dataDto.response.ConsultarCuentaResponseDto
import com.myclub.myapplication.dataDto.response.IniciarSesionResponseDto
import com.myclub.myapplication.databinding.ActivityIniciarSesionBinding
import com.myclub.myapplication.network.ApiClient
import com.myclub.myapplication.network.ApiService
import com.myclub.myapplication.utils.Constantes
import com.myclub.myapplication.utils.Constantes.BASE_URL_PERSONAS
import com.myclub.myapplication.utils.Constantes.ID_PROYECTO
import com.myclub.myapplication.utils.dataStore.MySharedPreferences
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class IniciarSesion : Activity() {
    private var signInRequestdDto: SignInRequestDto? = null
    private var signInResponseDto: IniciarSesionResponseDto? = null
    private lateinit var binding: ActivityIniciarSesionBinding
    private lateinit var login: String
    private lateinit var password: String
    private lateinit var queryPersonByIdResponseDto: ConsultarCuentaResponseDto
    private lateinit var sharedPreferences: MySharedPreferences
    private lateinit var personaRequest: PersonalModelDto


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityIniciarSesionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        queryPersonByIdResponseDto = ConsultarCuentaResponseDto()
        AlertLoading().alertLoadingDialog(this, "Validando")

        botones()

    }

    private fun botones() {
        binding.idBtnIngresar.setOnClickListener {
            if (ValidacionesLogin()) {
                iniciarsesion(binding.idusuario.text.toString(), binding.idpwd.text.toString())
            }
        }
        binding.idBtnRegistro.setOnClickListener {
            val i = Intent(this, Registro::class.java)
            startActivity(i)
        }
        binding.idrecuperarContrasena.setOnClickListener {
            val inte = Intent(this, RecuperarContra::class.java)
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


    private fun iniciarsesion(login: String, password: String) {

        alertDialogLoading.show()
        signInRequestdDto = SignInRequestDto()
        signInRequestdDto!!.Login = login
        signInRequestdDto!!.Password = password
        signInRequestdDto!!.IdProyecto = ID_PROYECTO
        //personaRequest.IdRol = Constantes.ID_ROL_PERSONA_NATURAL


        val apiService: ApiService =
            ApiClient.RetrofitHelper(BASE_URL_PERSONAS).create(ApiService::class.java)
        apiService.signInUser(signInRequestdDto)
            ?.enqueue(object : Callback<IniciarSesionResponseDto?> {
                override fun onResponse(
                    call: Call<IniciarSesionResponseDto?>,
                    response: Response<IniciarSesionResponseDto?>
                ) {

                    signInResponseDto = response.body()
                    sharedPreferences = MySharedPreferences(this@IniciarSesion)

                    if (signInResponseDto?.CodigoRespuesta == 500) {
                        AlertErrorResponse().alertErrorResponseDialog(
                            this@IniciarSesion,
                            "${signInResponseDto?.MensajeRespuesta}"
                        )
                        alertDialogErrorResponse.show()
                        alertDialogLoading.dismiss()

                    } else {
                        //sharedPreferences = MySharedPreferences(this@IniciarSesion)

                        sharedPreferences.storeIdUser(signInResponseDto?.Id.toString())
                        sharedPreferences.storeActiveSessionUser("ActiveSession")

                        jumpToViewDesition(signInResponseDto?.IdRol)
                        alertDialogLoading.dismiss()

                    }
                }


                override fun onFailure(call: Call<IniciarSesionResponseDto?>, t: Throwable) {
                    Toast.makeText(this@IniciarSesion, t.message, Toast.LENGTH_SHORT).show()

                    alertDialogLoading.dismiss()
                }
            })
    }

    private fun jumpToViewDesition(code: Double?) {
        try {
            val i: Intent
            when (code) {
                1.0 -> {
                    i = Intent(this@IniciarSesion, MainActivity::class.java)
                    startActivity(i)
                    finish()
                }
                2.0 -> {
                    i = Intent(this@IniciarSesion, MainActivityBusiness::class.java)
                    startActivity(i)
                    finish()
                }
            }
        } catch (e: Exception) {
            //
        }
    }
}