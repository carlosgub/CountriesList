package com.carlosgub.countries.domain.usecase

import com.carlosgub.countries.domain.model.Country
import com.carlosgub.countries.domain.repository.CountriesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

internal class GetCountriesByNameUseCase(
    private val countriesRepository: CountriesRepository
) {
    operator fun invoke(name: String): Flow<List<Country>> =
        flow { emit(countriesRepository.getCountriesByName(name)) }

}