package com.myclub.myapplication.dataDto.response

data class CuponComercioResponseDto(
    var IdCoupon: Double? = null,
    var DescriptionCoupon: String? = null,
    var IdTypeCoupon: String? = null,
    var IdStateCoupon: String? = null,
    var Categories: List<CategoriasResponseDto>? = null
)

data class CategoriasResponseDto(
    var IdCategory: Double? = null,
    var DescriptionCategory: String?  = null,
    var Trade: List<ComercioCategoriasResponseDto>? = null
)

data class ComercioCategoriasResponseDto(
    var IdTrade: Double? = null,
    var IdUser: Double? = null,
    var DescriptionTrade: String? = null,
    var DirectionTrade: String? = null,
    var IdCiudadTrade: String? = null,
    var IdCategory: Double? = null,
    var LogoTrade: String? = null,
    var IdProject: Double? = null,
    var IdCoupon: Double? = null,
    var IdPersonTrade: Double? = null,
)
