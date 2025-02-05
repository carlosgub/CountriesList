package com.carlosgub.countries.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Country(
    val name: CountryName,
    val flags: CountryFlag,
    val capital: List<String>? = null,
) {
    @Serializable
    data class CountryName(
        val common: String,
        val official: String,
    )

    @Serializable
    data class CountryFlag(
        val png:String,
    )
}