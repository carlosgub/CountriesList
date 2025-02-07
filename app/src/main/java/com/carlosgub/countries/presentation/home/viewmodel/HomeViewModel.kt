package com.carlosgub.countries.presentation.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.carlosgub.countries.core.sealed.GenericState
import com.carlosgub.countries.domain.usecase.GetCountriesListUseCase
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

internal class HomeViewModel(
    private val getCountriesListUseCase: GetCountriesListUseCase,
) : ViewModel() {
    private val _state = MutableStateFlow(HomeScreenState())
    val state = _state.asStateFlow()

    private var queryJob: Job? = null

    fun queryFieldChange(query: String) {
        _state.value = HomeScreenState.Loading

        queryJob?.cancel()

        queryJob = viewModelScope.launch {
            delay(300L)
            val countries = getCountriesListUseCase(query)
            _state.value = when (countries) {
                is GenericState.Error -> HomeScreenState.Error(countries.message)
                is GenericState.Success -> HomeScreenState.Success(countries.item)
            }
        }
    }
}
