package com.myclub.myapplication.network


import com.myclub.myapplication.dataDto.PersonalModelDto
import com.myclub.myapplication.dataDto.request.*
import com.myclub.myapplication.dataDto.response.*
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {

    @POST("/api/CuponesUsuario/BuyMembershipPlan")
    fun BuyPlan(@Body consultaBuyPlanDto: ConsultarBuyRequestDto?): Call<ConsultarBuyResponseDto?>

    @POST("/api/CuponesUsuario/GetListMyPlans")
    fun GetMyPlans(@Body consulta: ConsultaMisPlanesRequestDto?): Call<List<MisPlanesResponseDto>?>


    @POST("/api/CuponesUsuario/GetListMyCoupons")
    fun ConsultarVaucher(@Body consultaVaucherDto: ConsultarVaucherRequestDto?): Call<List<ConsultarVaucherResponseDto?>>

    @POST("/api/CuponComercio/AllShopsOfCoupon")
    fun ConsultarShops(@Body consultaShopDto: ConsultarShopsRequestDto): Call<List<ConsultarShopsResponseDto?>>

    @POST("/api/CuponComercio/CouponListAll")
    fun ConsultarCupon(@Body consultaCuponDto: ConsultaCuponRequestDto?): Call<List<ConsultarCuponResponseDto?>>


    @POST("/api/persona/CrearPersona")
    fun registerNewUser(@Body personaRequest: PersonalModelDto?): Call<ResponseDto?>?

    @POST("/api/persona/IniciarSesion")
    fun signInUser(@Body signInRequestDto: SignInRequestDto?): Call<IniciarSesionResponseDto?>?

    @POST("/api/Persona/BuscarPersonaPorIdPersona")
    fun ConsultarCuenta(@Body queryPersonById: ConsultarCuentaRequestDto?): Call<ConsultarCuentaResponseDto?>?

    @POST("/api/persona/VerificarCodigo")
    fun verifyCode(@Body verifyCoeRequestDto: VerifyCoeRequestDto?): Call<Boolean?>?

}