package com.myclub.myapplication.dataDto.request

data class VerifyCoeRequestDto(
    var idPersona: Double? = null,
    var IdProyecto: Double? = null,
    var CodigoVerificacion: String? = null,
    var Login: String? = null,
)