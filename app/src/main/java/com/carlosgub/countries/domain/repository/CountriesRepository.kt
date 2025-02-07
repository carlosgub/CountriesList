package com.carlosgub.countries.domain.repository

import com.carlosgub.countries.core.sealed.GenericState
import com.carlosgub.countries.domain.model.Country

internal interface CountriesRepository {
    suspend fun getAllCountries(): GenericState<List<Country>>

    suspend fun getCountriesByName(query: String): GenericState<List<Country>>
}
