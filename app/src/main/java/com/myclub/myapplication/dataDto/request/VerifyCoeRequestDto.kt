package com.myclub.myapplication.dataDto.request

data class VerifyCoeRequestDto(
    var IdPerson: Double? = null,
    var Login: String? = null,
    var IdProject: Double? = null,
    var CodeVerification: String? = null,
)