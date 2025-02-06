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
    val region: String? = null,
    val subregion: String? = null,
    val languages: Map<String, String>? = null,
    val currencies: Map<String, CountryCurrency>? = null,
    val coatOfArms: CountryCoatOfArms? = null,
    val population: Long,
    val car: CountryCar? = null
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

    @Serializable
    @Parcelize
    data class CountryCoatOfArms(
        val png: String? = null,
    ) : Parcelable

    @Serializable
    @Parcelize
    data class CountryCurrency(
        val name: String,
        val symbol: String,
    ) : Parcelable

    @Serializable
    @Parcelize
    data class CountryCar(
        val side: String? = null,
    ) : Parcelable

}
