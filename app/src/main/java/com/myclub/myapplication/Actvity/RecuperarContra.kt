package com.myclub.myapplication.Actvity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import com.myclub.myapplication.dataDto.request.ConsultarRecuperarRequestDto

import com.myclub.myapplication.dataDto.response.ConsultarRecuperarResponseDto
import com.myclub.myapplication.databinding.ActivityRecuperarContraBinding
import com.myclub.myapplication.network.ApiClient
import com.myclub.myapplication.network.ApiService
import com.myclub.myapplication.utils.Constantes
import com.myclub.myapplication.utils.Constantes.CODIGO_ERROR
import com.myclub.myapplication.utils.Constantes.CODIGO_EXITOSO
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
            if (binding.idTxtUserName.text.toString().isNullOrEmpty()) {
                esValido = false
                binding.idTxtUserName.error = Constantes.ERROR_FORMULARIO_VACIO
            } else {
                esValido = true
                binding.idTxtUserName.error = null
            }

            if (binding.idTxtUserEmail.text.toString().isNullOrEmpty()) {
                esValido = false
                binding.idTxtUserEmail.error = Constantes.ERROR_FORMULARIO_VACIO
            } else {
                esValido = true
                binding.idTxtUserEmail.error = null
            }

        } catch (e: Exception) {
            //
        }
        return esValido
    }
    private fun callService(login: String, email: String) {
        try {
            recuperarRequestDto = ConsultarRecuperarRequestDto()
            recuperarRequestDto!!.Email = email
            recuperarRequestDto!!.IdProyecto = Constantes.ID_PROYECTO
            recuperarRequestDto!!.Login = login


            AlertLoading.alertDialogLoading.show()


            val apiService: ApiService =
                ApiClient.RetrofitHelper(Constantes.BASE_URL_PERSONAS).create(ApiService::class.java)

            apiService.RecuperarContrasena(recuperarRequestDto)
                .enqueue(object : Callback<ConsultarRecuperarResponseDto?>{
                    override fun onResponse(
                        call: Call<ConsultarRecuperarResponseDto?>,
                        response: Response<ConsultarRecuperarResponseDto?>
                    ) {
                        recuperarResponseDto = response.body()
                        AlertLoading.alertDialogLoading.dismiss()

                        if (recuperarResponseDto?.Codigo == CODIGO_ERROR) {
                            AlertErrorResponse().alertErrorResponseDialog(
                                this@RecuperarContra,
                                "${recuperarResponseDto?.Mensaje}"
                            )
                            AlertErrorResponse.alertDialogErrorResponse.show()
                            AlertLoading.alertDialogLoading.dismiss()

                        } else {
                            (recuperarResponseDto?.Codigo == CODIGO_EXITOSO)
                            Toast.makeText(this@RecuperarContra, "siuuu", Toast.LENGTH_SHORT).show()
                            val i = Intent(this@RecuperarContra, IniciarSesion::class.java)
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
            AlertLoading.alertDialogLoading.dismiss()

        }
    }

    private fun validateEmail(email: String): Boolean {
        val pattern: Pattern = Patterns.EMAIL_ADDRESS
        pattern.matcher(email).matches()
        return pattern.matcher(email).matches()
    }

}