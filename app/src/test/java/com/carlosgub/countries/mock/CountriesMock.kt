package com.carlosgub.countries.mock

import com.carlosgub.countries.domain.model.Country

val peru =
    Country(
        name = Country.CountryName(
            common = "Peru",
            official = "Republic of Peru",
        ),
        capital = listOf("Lima"),
        flags = Country.CountryFlag(
            png = "https://flagcdn.com/w320/pe.png",
        ),
    )

val usa =
    Country(
        name = Country.CountryName(
            common = "United States Of America",
            official = "USA",
        ),
        capital = listOf("Washington"),
        flags = Country.CountryFlag(
            png = "https://flagcdn.com/w320/us.png",
        ),
    )

val countryList = listOf(usa, peru)
