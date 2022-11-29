package com.myclub.myapplication.network


import com.myclub.myapplication.dataDto.PersonalModelDto
import com.myclub.myapplication.dataDto.request.*
import com.myclub.myapplication.dataDto.response.*
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {

    //  PARA CAMBIAR CONTRASEÑA
    @POST("/apiamingenieria.app/api/Usuario/CambiarContraseniaUsuario")
    fun CambiarContra(@Body cambiarDto: CambiarContraRequestDto?): Call<ResponseDto?>



    //  PARA CANJEAR QR
    @POST("/api/Comercios/RedeemUserCoupon")
    fun CanjearQR(@Body requestRedimirDto: RedimirCuponUsuarioDto?): Call<CanjearQRResponseDto?>


    //  PARA RECUPERAR CONRASEÑA
    @POST("/api/Usuario/GenerarNuevaContraseniaUsuarioOlvidado")
    fun RecuperarContrasena(@Body consultaRecuperarDto: ConsultarRecuperarRequestDto?): Call<ConsultarRecuperarResponseDto?>


    // LISTA DE CUPONES PARA ACTIVAR MEMBRESIA AL DAR CLIC
    @POST("/api/CuponesUsuario/BuyMembershipPlan")
    fun BuyPlan(@Body consultaBuyPlanDto: ConsultarBuyRequestDto?): Call<ConsultarBuyResponseDto?>

    // LISTA DE MIS MEMBRESIAS
    @POST("/api/CuponesUsuario/GetListMyPlans")
    fun GetMyPlans(@Body consulta: ConsultaMisPlanesRequestDto?): Call<List<MisPlanesResponseDto>?>


    // LISTA DE MIS CUPONES PARA PODER CANJEAR
    @POST("/api/CuponesUsuario/GetListMyCoupons")
    fun ConsultarVaucher(@Body consultaVaucherDto: ConsultarVaucherRequestDto?): Call<List<ConsultarVaucherResponseDto?>>


    // LISTA DE CUPONES PARA ACTIVAR MEMBRESIA
    @POST("/api/CuponComercio/AllShopsOfCoupon")
    fun ConsultarShops(@Body consultaShopDto: ConsultarShopsRequestDto): Call<List<ConsultarShopsResponseDto?>>


    // LISTA DE MEMBRESIAS
    @POST("/api/CuponComercio/CouponListAll")
    fun ConsultarCupon(@Body consultaCuponDto: ConsultaCuponRequestDto?): Call<List<ConsultarCuponResponseDto?>>


    // REGISTRO PERSONAS
    @POST("/api/persona/CrearPersona")
    fun registerNewUser(@Body personaRequest: PersonalModelDto?): Call<ResponseDto?>?


    // INICIAR SESION
    @POST("/api/persona/IniciarSesion")
    fun signInUser(@Body signInRequestDto: SignInRequestDto?): Call<IniciarSesionResponseDto?>?

    @POST("/api/Persona/BuscarPersonaPorIdPersona")
    fun ConsultarCuenta(@Body queryPersonById: ConsultarCuentaRequestDto?): Call<ConsultarCuentaResponseDto?>?

    // VERIFICAR CODIGO DE REGISTRO
    @POST("/api/persona/VerificarCodigo")
    fun verifyCode(@Body verifyCoeRequestDto: VerifyCoeRequestDto?): Call<Boolean?>?

}