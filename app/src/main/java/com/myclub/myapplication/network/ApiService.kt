package com.myclub.myapplication.network


import com.myclub.myapplication.dataDto.PersonalModelDto
import com.myclub.myapplication.dataDto.request.ConsultaCuponRequestDto
import com.myclub.myapplication.dataDto.request.ConsultarCuentaRequestDto
import com.myclub.myapplication.dataDto.request.SignInRequestDto
import com.myclub.myapplication.dataDto.request.VerifyCoeRequestDto
import com.myclub.myapplication.dataDto.response.ConsultarCuponResponseDto
import com.myclub.myapplication.dataDto.response.IniciarSesionResponseDto
import com.myclub.myapplication.dataDto.response.ResponseDto
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {

    @POST("/api/CuponComercio/CouponListAll")
    fun ConsultarCupon(@Body consultaCuponDto: ConsultaCuponRequestDto?): Call<List<ConsultarCuponResponseDto?>>


    @POST("/api/persona/CrearPersona")
    fun registerNewUser(@Body personaRequest: PersonalModelDto?): Call<ResponseDto?>?

    @POST("/api/persona/IniciarSesion")
    fun signInUser(@Body signInRequestDto: SignInRequestDto?): Call<IniciarSesionResponseDto?>?

    @POST("/api/Persona/BuscarPersonaPorIdPersona")
    fun queryPersonByIdRequestDto(@Body queryPersonById: ConsultarCuentaRequestDto?): Call<ConsultarCuentaRequestDto?>?

    @POST("/api/persona/VerificarCodigo")
    fun verifyCode(@Body verifyCoeRequestDto: VerifyCoeRequestDto?): Call<Boolean?>?

}