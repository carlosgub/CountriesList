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
        region = "Americas",
        subregion = "South America",
        languages = mapOf(
            "spa" to "Spanish",
        ),
        currencies = mapOf(
            "PEN" to Country.CountryCurrency(
                name = "Peruvian Sol",
                symbol = "S/.",
            ),
        ),
        population = 32971849,

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
        region = "Americas",
        subregion = "North America",
        languages = mapOf(
            "eng" to "English",
        ),
        currencies = mapOf(
            "USD" to Country.CountryCurrency(
                name = "United States Dollar",
                symbol = "$",
            ),
        ),
        population = 32971849,
    )

val countryList = listOf(usa, peru)
