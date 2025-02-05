package com.carlosgub.countries.data.datasource.remote

import com.carlosgub.countries.domain.model.Country
import retrofit2.Retrofit

internal class CountriesRemoteDataSource(
    private val retrofit: Retrofit,
) {
    suspend fun getAllCountries(): List<Country> {
        return retrofit.create(CountriesService::class.java)
            .getAllCountries()
            .body() ?: listOf()
    }

    suspend fun getCountriesByName(query: String): List<Country> {
        return retrofit.create(CountriesService::class.java)
            .getCountriesByName(query)
            .body() ?: listOf()
    }
}
