package com.carlosgub.countries.presentation.home.viewmodel

import com.carlosgub.countries.domain.model.Country

open class HomeScreenState {
    data class Success(
        val countriesList: List<Country> = listOf()
    ) : HomeScreenState()

    data class Error(val message: String) :
        HomeScreenState()

    object Loading : HomeScreenState()

}