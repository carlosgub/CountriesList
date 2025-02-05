package com.carlosgub.countries.data.repository

import com.carlosgub.countries.data.datasource.remote.CountriesRemoteDataSource
import com.carlosgub.countries.domain.model.Country
import com.carlosgub.countries.domain.repository.CountriesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

internal class CountriesRepositoryImpl(
    private val countriesRemoteDataSource: CountriesRemoteDataSource
) : CountriesRepository {
    override suspend fun getAllCountries(): List<Country> =
        withContext(Dispatchers.IO) {
            countriesRemoteDataSource.getAllCountries()
        }

    override suspend fun getCountriesByName(query: String): List<Country> =
        withContext(Dispatchers.IO) {
            countriesRemoteDataSource.getCountriesByName(query)
        }
}