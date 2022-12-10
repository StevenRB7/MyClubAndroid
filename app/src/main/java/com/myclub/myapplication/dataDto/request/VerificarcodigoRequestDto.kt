package com.myclub.myapplication.dataDto.request

data class VerificarcodigoRequestDto(
    var IdPerson: String? = null,
    var Login: String? = null,
    var Email: String? = null,
    var IdProject: String? = null,
    var CodeVerification: String? = null,
    var IsRecoverPassword: Boolean? = null,

    )
