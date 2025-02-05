package com.carlosgub.countries.domain.usecase

import com.carlosgub.countries.domain.model.Country
import com.carlosgub.countries.domain.repository.CountriesRepository

internal class GetAllCountriesUseCase(
    private val countriesRepository: CountriesRepository
) {
    suspend operator fun invoke(): List<Country> =
        countriesRepository.getAllCountries()

}