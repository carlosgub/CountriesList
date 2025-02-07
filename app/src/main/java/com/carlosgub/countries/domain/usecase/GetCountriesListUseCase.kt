package com.carlosgub.countries.domain.usecase

import com.carlosgub.countries.core.sealed.GenericState
import com.carlosgub.countries.domain.model.Country
import com.carlosgub.countries.domain.repository.CountriesRepository

internal class GetCountriesListUseCase(
    private val countriesRepository: CountriesRepository,
) {
    suspend operator fun invoke(query: String): GenericState<List<Country>> =
        if (query.length >= 2 && query.trim().isNotEmpty()) {
            countriesRepository.getCountriesByName(query)
        } else {
            countriesRepository.getAllCountries()
        }
}
