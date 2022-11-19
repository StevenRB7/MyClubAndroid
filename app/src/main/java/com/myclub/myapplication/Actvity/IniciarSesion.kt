package com.myclub.myapplication.Actvity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import com.myclub.myapplication.Actvity.AlertErrorResponse.Companion.alertDialogErrorResponse
import com.myclub.myapplication.Actvity.AlertLoading.Companion.alertDialogLoading
import com.myclub.myapplication.MainActivity

import com.myclub.myapplication.dataDto.request.SignInRequestDto
import com.myclub.myapplication.dataDto.response.ConsultarCuentaResponseDto
import com.myclub.myapplication.dataDto.response.IniciarSesionResponseDto
import com.myclub.myapplication.databinding.ActivityIniciarSesionBinding
import com.myclub.myapplication.network.ApiClient
import com.myclub.myapplication.network.ApiService
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
            iniciarsesion(binding.idusuario.text.toString(), binding.idpwd.text.toString())
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

    private fun iniciarsesion(login: String, password: String) {

        alertDialogLoading.show()
        signInRequestdDto = SignInRequestDto()
        signInRequestdDto!!.Login = login
        signInRequestdDto!!.Password = password
        signInRequestdDto!!.IdProyecto = ID_PROYECTO

        val apiService: ApiService =
            ApiClient.RetrofitHelper(BASE_URL_PERSONAS).create(ApiService::class.java)
        apiService.signInUser(signInRequestdDto)
            ?.enqueue(object : Callback<IniciarSesionResponseDto?> {
                override fun onResponse(
                    call: Call<IniciarSesionResponseDto?>,
                    response: Response<IniciarSesionResponseDto?>
                ) {

                    if (signInResponseDto?.CodigoRespuesta == 500) {
                        AlertErrorResponse().alertErrorResponseDialog(
                            this@IniciarSesion,
                            "${signInResponseDto?.MensajeRespuesta}"
                        )
                        alertDialogErrorResponse.show()
                       alertDialogLoading.dismiss()

                    } else {
                        sharedPreferences = MySharedPreferences(this@IniciarSesion)

                        sharedPreferences.storeIdUser(signInResponseDto?.Id.toString())
                        sharedPreferences.storeActiveSessionUser("ActiveSession")
                       alertDialogLoading.dismiss()
                        val i = Intent(this@IniciarSesion, MainActivity::class.java)
                        startActivity(i)
                    }
                }

                override fun onFailure(call: Call<IniciarSesionResponseDto?>, t: Throwable) {
                    Toast.makeText(this@IniciarSesion, t.message, Toast.LENGTH_SHORT).show()

                   alertDialogLoading.dismiss()
                }
            })

    }
}