package com.myclub.myapplication.Actvity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import com.myclub.myapplication.Actvity.AlertErrorResponse.Companion.alertDialogErrorResponse
import com.myclub.myapplication.Actvity.AlertLoading.Companion.alertDialogLoading
import com.myclub.myapplication.R
import com.myclub.myapplication.dataDto.PersonalModelDto
import com.myclub.myapplication.dataDto.response.ResponseDto
import com.myclub.myapplication.databinding.ActivityRegistroBinding
import com.myclub.myapplication.network.ApiClient
import com.myclub.myapplication.network.ApiService
import com.myclub.myapplication.utils.Constantes.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.regex.Pattern

class Registro : AppCompatActivity() {

    private lateinit var binding: ActivityRegistroBinding
    private lateinit var personaRequest: PersonalModelDto
    private lateinit var responseDto: ResponseDto



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)

        binding = ActivityRegistroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        buttonActions();
    }
    private fun callUserRegistrationService() {
        try {
            setData()
            responseDto = ResponseDto()
            alertDialogLoading.show()
            val apiService: ApiService =
                ApiClient.RetrofitHelper(BASE_URL_PERSONAS)
                    .create(ApiService::class.java)
            apiService.registerNewUser(personaRequest)?.enqueue(object : Callback<ResponseDto?> {
                override fun onResponse(
                    call: Call<ResponseDto?>,
                    response: Response<ResponseDto?>
                ) {
                    responseDto = response.body()!!
                    Toast.makeText(this@Registro, "${responseDto.Codigo}", Toast.LENGTH_SHORT).show()
                    alertDialogLoading.dismiss()

                    if (responseDto.Codigo == CODIGO_ERROR) {

                        AlertErrorResponse().alertErrorResponseDialog(
                            this@Registro,
                            responseDto.Mensaje.toString() + " ${personaRequest.Telefono}"
                        )
                        alertDialogErrorResponse.show()

                    } else {
                        val i = Intent(this@Registro, VerifyCodeActivity::class.java)
                        i.putExtra("IdPersona", responseDto.IdPersona)
                        i.putExtra("PhonePerson", binding.idTxtTelefono.text.toString())
                        i.putExtra("EmailUser", binding.idTxtCorreo.text.toString())
                        startActivity(i)
                        alertDialogLoading.show()

                    }
                }

                override fun onFailure(call: Call<ResponseDto?>, t: Throwable) {
                    alertDialogLoading.dismiss()
                }
            })
        } catch (e: Exception) {
            alertDialogLoading.dismiss()
        }
    }
    private fun buttonActions() {
        binding.idBtnRegistrarme.setOnClickListener {
            if (validateTextFields()) {
                if (validateEmail(binding.idTxtCorreo.text.toString())) {
                    callUserRegistrationService()
                } else {
                    binding.idTxtCorreo.error = E_EMAIL_INVALID
                }
            }
        }
        var btn : ImageView = findViewById(R.id.btnregresar)
        btn.setOnClickListener {
            val intent = Intent(this, IniciarSesion::class.java)
            startActivity(intent)
        }
        var iniciar : Button = findViewById(R.id.btnIniciarSesion2)
        iniciar.setOnClickListener {
            val i = Intent(this, IniciarSesion::class.java)
            startActivity(i)
        }
    }
    private fun setData() {
        try {
            personaRequest = PersonalModelDto()
            personaRequest.PrimerNombre = binding.idTxtNombre.text.toString()
            personaRequest.SegundoNombre = ""
            personaRequest.PrimerApellidos = ""
            personaRequest.SegundoApellido = ""
            personaRequest.Sexo = ""
            personaRequest.FechaNacimiento = ""
            personaRequest.TipoDocumento = 1.0
            personaRequest.Documento = binding.idTxtNumberIdentification.text.toString()
            personaRequest.EstadoCivil = ""
            personaRequest.Telefono = binding.idTxtTelefono.text.toString()
            personaRequest.Celular = binding.idTxtTelefono.text.toString()
            personaRequest.Direccion = ""
            personaRequest.Correo = binding.idTxtCorreo.text.toString()
            personaRequest.IdProyecto = ID_PROYECTO
            personaRequest.IdRol = ID_ROL_PERSONA_NATURAL
        } catch (e: Exception) {
            Toast.makeText(this, "ErrorSetDataTransform", Toast.LENGTH_SHORT).show()
        }
    }

    private fun validateTextFields(): Boolean {
        var esValido = true
        try {
            if (binding.idTxtNombre.text.toString().isNullOrEmpty()) {
                esValido = false
                binding.idTxtNombre.error = ERROR_FORMULARIO_VACIO
            } else {
                esValido = true
                binding.idTxtNombre.error = null
            }

            if (binding.idTxtTelefono.text.toString()
                    .isNullOrEmpty() && binding.idTxtTelefono.text.toString().length >= 10
            ) {
                esValido = false
                binding.idTxtTelefono.error = ERROR_FORMULARIO_VACIO
            } else {
                esValido = true
                binding.idTxtTelefono.error = null
            }

            if (binding.idTxtCorreo.text.toString().isNullOrEmpty()) {
                esValido = false
                binding.idTxtCorreo.error = ERROR_FORMULARIO_VACIO
            } else {
                esValido = true
                binding.idTxtCorreo.error = null
            }

            if (binding.idTxtNumberIdentification.text.toString().isNullOrEmpty()) {
                esValido = false
                binding.idTxtNumberIdentification.error = ERROR_FORMULARIO_VACIO
            } else {
                esValido = true
                binding.idTxtNumberIdentification.error = null
            }
        } catch (e: Exception) {
            esValido = false
        }
        return esValido
    }

    private fun validateEmail(email: String): Boolean {
        val pattern: Pattern = Patterns.EMAIL_ADDRESS
        pattern.matcher(email).matches()
        return pattern.matcher(email).matches()
    }
}