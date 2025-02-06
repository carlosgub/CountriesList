@file:OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)

package com.carlosgub.countries.presentation.home.viewmodel

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.carlosgub.countries.domain.usecase.GetAllCountriesUseCase
import com.carlosgub.countries.domain.usecase.GetCountriesByNameUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

internal class HomeViewModel(
    private val getAllCountriesUseCase: GetAllCountriesUseCase,
    private val getCountriesByNameUseCase: GetCountriesByNameUseCase,
) : ViewModel() {
    private val _state = MutableStateFlow(HomeScreenState())
    val state = _state.asStateFlow()

    init {
        getAllCountries()
        search()
    }

    @VisibleForTesting
    fun getAllCountries() {
        viewModelScope.launch {
            showLoading(true)
            _state.value = _state.value.copy(
                allCountries = getAllCountriesUseCase(),
            )
            showLoading(false)
        }
    }

    fun queryFieldChange(query: String) {
        viewModelScope.launch {
            showLoading(query.length >= 2)
            _state.value = _state.value.copy(query = query)
        }
    }

    private fun search() {
        viewModelScope.launch {
            state
                .map { it.query }
                .filter { query ->
                    query.length >= 2 && query.trim().isEmpty().not()
                }
                .debounce(300L)
                .distinctUntilChanged()
                .flatMapLatest { query ->
                    getCountriesByNameUseCase(query)
                }
                .collect { countries ->
                    _state.value = _state.value.copy(countriesByName = countries)
                    showLoading(false)
                }
        }
    }

    private fun showLoading(show: Boolean) {
        _state.value = _state.value.copy(showLoading = show)
    }
}
