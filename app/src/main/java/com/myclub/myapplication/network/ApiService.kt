package com.myclub.myapplication.network


import com.myclub.myapplication.dataDto.PersonalModelDto
import com.myclub.myapplication.dataDto.request.*
import com.myclub.myapplication.dataDto.response.*
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {

    //  PARA CAMBIAR CONTRASEÑA
    @POST("/api/User/ChangeYourPassword")
    fun CambiarContra(@Body cambiarDto: CambiarContraRequestDto?): Call<ResponseDto?>

 //  PARA CAMBIAR CONTRASEÑA VERIFICAR CODE
    @POST("/api/User/VerifyCode")
    fun CambiarContraCode(@Body cambiarveriDto: VerificarcodigoRequestDto?): Call<ResponseDto?>?


//  ENVIAR CODIGO PA VERIFICAR CAMBIO DE CONTRA
   @POST("/api/User/SendCodeVerification")
    fun ContraVeriCode(@Body veriDto: SendCodeRequestDto?): Call<ResponseDto?>?


    @POST("/api/Persona/BuscarPersonaPorIdPersona")
    fun queryPersonByIdRequestDto(@Body queryPersonById: QueryPersonByIdRequestDto?): Call<QueryPersonByIdResponseDto?>?


    //  PARA CANJEAR QR
    @POST("/api/Comercios/RedeemUserCoupon")
    fun CanjearQR(@Body requestRedimirDto: RedimirCuponUsuarioDto?): Call<CanjearQRResponseDto?>


    // LISTA DE CUPONES PARA ACTIVAR MEMBRESIA AL DAR CLIC
    @POST("/api/CuponesUsuario/BuyMembershipPlan")
    fun BuyPlan(@Body consultaBuyPlanDto: ConsultarBuyRequestDto?): Call<ConsultarBuyResponseDto?>


    // LISTA DE MIS MEMBRESIAS
    @POST("/api/CuponesUsuario/GetListMyPlans")
    fun GetMyPlans(@Body consulta: ConsultaMisPlanesRequestDto?): Call<List<MisPlanesResponseDto>?>


    // LISTA DE MIS CUPONES PARA PODER CANJEAR
    @POST("/api/CuponesUsuario/GetListMyCoupons")
    fun ConsultarVaucher(@Body consultaVaucherDto: ConsultarVaucherRequestDto?): Call<List<ConsultarVaucherResponseDto?>>


    //LISTADO DE MIS COMERCIOS ASOCIADOS POR PLAN COMPRADO
    @POST("/api/CuponesUsuario/GetListMyCouponsOrTrades")
    fun consultarMisComerciosAsociados(@Body consultar: ConsultarVaucherRequestDto?): Call<CuponComercioResponseDto?>


    // LISTA DE CUPONES PARA ACTIVAR MEMBRESIA
    @POST("/api/CuponComercio/AllShopsOfCoupon")
    fun ConsultarShops(@Body consultaShopDto: ConsultarShopsRequestDto): Call<List<ConsultarShopsResponseDto?>>


    // LISTA DE MEMBRESIAS
    @POST("/api/CuponComercio/CouponListAll")
    fun ConsultarCupon(@Body consultaCuponDto: ConsultaCuponRequestDto?): Call<List<ConsultarCuponResponseDto?>>


    // REGISTRO PERSONAS
    @POST("/api/User/RegisterNewUser")
    fun registerNewUser(@Body personaRequest: PersonalModelDto?): Call<ResponseDto?>?

 // REGISTRO PERSONAS
 @POST("/api/User/RegisterNewUser")
 fun registerNewUserr(@Body personaRequest: PersonalModelDto?): Call<PersonalModelDto?>?

    // INICIAR SESION
    @POST("/api/User/SignIn")
    fun signInUser(@Body signInRequestDto: SignInRequestDto?): Call<ResponseDto?>?



}