package com.carlosgub.countries.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
data class Country(
    val name: CountryName,
    val flags: CountryFlag,
    val capital: List<String>? = null,
) : Parcelable {
    @Serializable
    @Parcelize
    data class CountryName(
        val common: String,
        val official: String,
    ) : Parcelable

    @Serializable
    @Parcelize
    data class CountryFlag(
        val png: String,
    ) : Parcelable
}
