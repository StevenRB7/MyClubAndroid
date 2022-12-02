package com.myclub.myapplication.Actvity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.myclub.myapplication.R
import com.myclub.myapplication.dataDto.PersonalModelDto
import com.myclub.myapplication.dataDto.response.ResponseDto
import com.myclub.myapplication.databinding.ActivityRegistroBinding
import com.myclub.myapplication.databinding.AlertLoadingBinding
import com.myclub.myapplication.network.ApiClient
import com.myclub.myapplication.network.ApiService
import com.myclub.myapplication.utils.Constantes.*
import com.myclub.myapplication.utils.alerts.AlertErrorResponse
import com.myclub.myapplication.utils.alerts.AlertErrorResponse.Companion.alertDialogErrorResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.regex.Pattern

class Registro : AppCompatActivity() {

    private lateinit var binding: ActivityRegistroBinding
    private lateinit var personaRequest: PersonalModelDto
    private lateinit var responseDto: ResponseDto
    private lateinit var alertDialogLoading: AlertDialog



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        alertLoadingShow()
        buttonActions();
        AlertLoading().alertLoadingDialog(this, M_VALIDATE_CODE)


    }
    private fun callUserRegistrationService() {
        try {
            setData()
            alertDialogLoading.show()
            val apiService: ApiService =
                ApiClient.RetrofitHelper(BASE_URL_PERSONAS)
                    .create(ApiService::class.java)
            apiService.registerNewUser(personaRequest)
                ?.enqueue(object : Callback<ResponseDto?> {
                override fun onResponse(
                    call: Call<ResponseDto?>,
                    response: Response<ResponseDto?>
                ) {
                    alertDialogLoading.dismiss()
                    if (response.body() != null) {
                        responseDto = response.body()!!
                        if (responseDto.Codigo == CODIGO_ERROR) {

                            alertDialogErrorResponse.show()

                            AlertErrorResponse().alertErrorResponseDialog(

                                this@Registro,
                                responseDto.Mensaje.toString() + " ${personaRequest.Telefono}"

                            )
                            //alertDialogErrorResponse.dismiss()


                        } else {
                            val i = Intent(this@Registro, VerifyCodeActivity::class.java)
                            i.putExtra("IdPersona", responseDto.IdPersona)
                            i.putExtra("PhonePerson", binding.idTxtTelefono.text.toString())
                            i.putExtra("EmailUser", binding.idTxtCorreo.text.toString())
                            startActivity(i)
                        }
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
        val btn : ImageView = findViewById(R.id.btnregresar)
        btn.setOnClickListener {
            val intent = Intent(this, IniciarSesion::class.java)
            startActivity(intent)
            finish()

        }
        val iniciar : Button = findViewById(R.id.btnIniciarSesion2)
        iniciar.setOnClickListener {
            val i = Intent(this, IniciarSesion::class.java)
            startActivity(i)
            finish()

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
            Toast.makeText(this, "Error de datos", Toast.LENGTH_SHORT).show()
        }
    }

    private fun validateTextFields(): Boolean {
        var esValido = true
        try {
            if (binding.idTxtNombre.text.toString().isEmpty()) {
                esValido = false
                binding.idTxtNombre.error = ERROR_FORMULARIO_VACIO
            } else {
                esValido = true
                binding.idTxtNombre.error = null
            }

            if (binding.idTxtTelefono.text.toString().isEmpty()
                && binding.idTxtTelefono.text.toString().length > 10
            ) {
                esValido = false
                binding.idTxtTelefono.error = ERROR_FORMULARIO_VACIO
            } else {
                if (binding.idTxtTelefono.text.toString().isEmpty()) {
                } else {
                    esValido = true
                    binding.idTxtTelefono.error = null
                }

            }

            if (binding.idTxtCorreo.text.toString().isEmpty()) {
                esValido = false
                binding.idTxtCorreo.error = ERROR_FORMULARIO_VACIO
            } else {
                esValido = true
                binding.idTxtCorreo.error = null
            }

            if (binding.idTxtNumberIdentification.text.toString().isEmpty()) {
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


    private fun alertLoadingShow() {
        try {
            val viewAlert = AlertLoadingBinding.inflate(layoutInflater)
            alertDialogLoading = AlertDialog.Builder(this).apply {
                setView(viewAlert.root)
                setCancelable(false)
            }.create()
            viewAlert.idTxtxMessage.text = "Validando datos por favor espere"
            alertDialogLoading.window?.setBackgroundDrawableResource(R.color.transparente)
        } catch (e: Exception) {
            //
        }
    }
}