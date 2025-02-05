package com.carlosgub.countries.domain.repository

import com.carlosgub.countries.domain.model.Country

internal interface CountriesRepository {
    suspend fun getAllCountries(): List<Country>

    suspend fun getCountriesByName(query: String): List<Country>
}
