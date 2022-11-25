package com.myclub.myapplication.Actvity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import com.myclub.myapplication.dataDto.request.ConsultarRecuperarRequestDto

import com.myclub.myapplication.dataDto.response.ConsultarRecuperarResponseDto
import com.myclub.myapplication.databinding.ActivityRecuperarContraBinding
import com.myclub.myapplication.network.ApiClient
import com.myclub.myapplication.network.ApiService
import com.myclub.myapplication.utils.Constantes
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.regex.Pattern

class RecuperarContra : AppCompatActivity() {
    private lateinit var binding: ActivityRecuperarContraBinding

    private var recuperarRequestDto: ConsultarRecuperarRequestDto? = null
    private var recuperarResponseDto: ConsultarRecuperarResponseDto? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        binding = ActivityRecuperarContraBinding.inflate(layoutInflater)
        setContentView(binding.root)
        BotonesDeRecuperar()

    }

    private fun BotonesDeRecuperar() {
        binding.idtxtregistrarse.setOnClickListener {
            val i = Intent(this, Registro::class.java)
            startActivity(i)
            finish()
        }
        binding.idBtnRecuperarPasword.setOnClickListener{
            if (validateTextFields()) {
                if (validateEmail(binding.idTxtUserEmail.text.toString())) {
                    callService(binding.idTxtUserName.text.toString(), binding.idTxtUserEmail.text.toString())
                } else {
                    binding.idTxtUserEmail.error = Constantes.E_EMAIL_INVALID
                }
            }
        }
    }
    private fun validateTextFields(): Boolean {
        var esValido = true
        try {
            if (binding.idTxtUserName.text.toString().isEmpty()) {
                esValido = false
                binding.idTxtUserName.error = Constantes.ERROR_FORMULARIO_VACIO
            } else {
                esValido = true
                binding.idTxtUserName.error = null
            }

            if (binding.idTxtUserEmail.text.toString().isEmpty()) {
                esValido = false
                binding.idTxtUserEmail.error = Constantes.ERROR_FORMULARIO_VACIO
            } else {
                esValido = true
                binding.idTxtUserEmail.error = null
            }

        } catch (e: Exception) {
            esValido = false
        }
        return esValido
    }
    private fun callService(login: String, email: String) {
        try {
            recuperarRequestDto = ConsultarRecuperarRequestDto()
            recuperarRequestDto!!.Email = 0.0
            recuperarRequestDto!!.IdProyecto = Constantes.ID_PROYECTO
            recuperarRequestDto!!.Login = login

            val apiService: ApiService =
                ApiClient.RetrofitHelper(Constantes.BASE_MY_CLUB).create(ApiService::class.java)

            apiService.RecuperarContrasena(recuperarRequestDto)
                .enqueue(object : Callback<ConsultarRecuperarResponseDto?>{
                    override fun onResponse(
                        call: Call<ConsultarRecuperarResponseDto?>,
                        response: Response<ConsultarRecuperarResponseDto?>
                    ) {
                        recuperarResponseDto = response.body()

                        if (recuperarResponseDto?.Codigo == 500) {
                            AlertErrorResponse().alertErrorResponseDialog(
                                this@RecuperarContra,
                                "${recuperarResponseDto?.Mensaje}"
                            )
                            AlertErrorResponse.alertDialogErrorResponse.show()
                            AlertLoading.alertDialogLoading.dismiss()

                        } else {

                            val i = Intent(this@RecuperarContra, IniciarSesion::class.java)
                            i.putExtra("IdPersona", recuperarResponseDto?.IdPersona)
                            i.putExtra("EmailUser", binding.idTxtUserEmail.text.toString())

                            startActivity(i)
                        }

                    }

                    override fun onFailure(
                        call: Call<ConsultarRecuperarResponseDto?>,
                        t: Throwable
                    ) {
                    }

                })

    } catch (e: Exception){

        }
    }

    private fun validateEmail(email: String): Boolean {
        val pattern: Pattern = Patterns.EMAIL_ADDRESS
        pattern.matcher(email).matches()
        return pattern.matcher(email).matches()
    }

}