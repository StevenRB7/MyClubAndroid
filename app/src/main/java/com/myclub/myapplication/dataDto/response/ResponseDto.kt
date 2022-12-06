package com.myclub.myapplication.dataDto.response

data class ResponseDto(
    var CodeResponse: Int? = 0,
    var MessageResponse: String? = null,
    val Data: DataExtraResponseDto? = null,
)

data class DataExtraResponseDto(
    var IdPerson: String? = null,
    var Names: String? = null,
    var Sex: String? = null,
    var DateOfBirth: String? = null,
    var DocumentType: String? = null,
    var Document: String? = null,
    var MaritalStatus: String? = null,
    var Phone: String? = null,
    var CellPhone: String? = null,
    var Direction: String? = null,
    var Email: String? = null,
    var IdProject: String? = null,
    var StatusUser: Boolean? = null,
    var Password: String? = null,
    var IdRoleUser: String? = null,
    var CodeVerification: String? = null,
)
