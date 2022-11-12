package com.myclub.myapplication.Actvity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.myclub.myapplication.MainActivity

import com.myclub.myapplication.dataDto.request.SignInRequestDto
import com.myclub.myapplication.dataDto.response.ConsutarCuentaResponseDto
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
    private lateinit var queryPersonByIdResponseDto: ConsutarCuentaResponseDto
    private lateinit var sharedPreferences: MySharedPreferences


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityIniciarSesionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        queryPersonByIdResponseDto =ConsutarCuentaResponseDto()




        //var crear : Button = findViewById(R.id.idBtnRegistro)
        //crear.setOnClickListener {
            //val intent = Intent(this, Registro::class.java)
        //startActivity(intent)
        //}

        //var iniciar : Button = findViewById(R.id.idBtnIngresar)
       //iniciar.setOnClickListener {
            //val intent = Intent(this, MainActivity::class.java)
            //startActivity(intent)
          // AlertLoading.alertDialogLoading.dismiss()

       //}

        botones()

    }

    private fun botones() {
        binding.idBtnIngresar.setOnClickListener {
            iniciarsesion(binding.idusuario.text.toString(), binding.idpwd.text.toString())
        }

    binding.idBtnRegistro.setOnClickListener {
    val i = Intent (this, Registro::class.java)
    startActivity(i)
        }
    }

    private fun iniciarsesion(login: String, password: String) {

        //AlertLoading.alertDialogLoading.show()
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
                    signInResponseDto = response.body()
                    sharedPreferences = MySharedPreferences(this@IniciarSesion)

                    if (signInResponseDto?.CodigoRespuesta == 500) {
                       // alertDialogErrorResponse.show()
                        //AlertLoading.alertDialogLoading.dismiss()

                    } else {
                        sharedPreferences.storeIdUser(signInResponseDto?.Id.toString())
                        sharedPreferences.storeActiveSessionUser("ActiveSession")
                        //AlertLoading.alertDialogLoading.dismiss()
                        val i = Intent(this@IniciarSesion, MainActivity::class.java)
                        startActivity(i)
                    }
                }

                override fun onFailure(call: Call<IniciarSesionResponseDto?>, t: Throwable) {
                    Toast.makeText(this@IniciarSesion, t.message, Toast.LENGTH_SHORT).show()
                    //binding.idProgresBarLogin.visibility = View.GONE

                    //AlertLoading.alertDialogLoading.dismiss()
                }
            })

    }
}