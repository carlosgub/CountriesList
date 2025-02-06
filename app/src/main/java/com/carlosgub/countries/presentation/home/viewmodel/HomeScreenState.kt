package com.carlosgub.countries.presentation.home.viewmodel

import com.carlosgub.countries.domain.model.Country

data class HomeScreenState(
    val allCountries: List<Country> = listOf(),
    val countriesByName: List<Country> = listOf(),
    val showLoading: Boolean = false,
    val query: String = "",
)
