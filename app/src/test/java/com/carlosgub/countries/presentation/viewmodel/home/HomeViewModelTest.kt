@file:OptIn(ExperimentalCoroutinesApi::class)

package com.carlosgub.countries.presentation.viewmodel.home

import com.carlosgub.countries.domain.usecase.GetAllCountriesUseCase
import com.carlosgub.countries.domain.usecase.GetCountriesByNameUseCase
import com.carlosgub.countries.mock.countryList
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class HomeViewModelTest {
    private lateinit var viewModel: HomeViewModel
    private val getAllCountriesUseCase: GetAllCountriesUseCase = mockk(relaxed = true)
    private val getCountriesByNameUseCase: GetCountriesByNameUseCase = mockk(relaxed = true)

    @Before
    fun setUp() {
        viewModel = HomeViewModel(getAllCountriesUseCase, getCountriesByNameUseCase)
    }

    @Test
    fun `getAllCountries should update state with countries`() = runTest {
        // Given
        coEvery { getAllCountriesUseCase.invoke() } returns countryList

        // When
        viewModel.getAllCountries()

        // Then
        val updatedState = viewModel.state.first { it.allCountries.isNotEmpty() }
        assertEquals(countryList, updatedState.allCountries)
    }

    @Test
    fun `queryFieldChange should update query state and show loading`() = runTest {
        // Given
        val query = "query"

        // When
        viewModel.queryFieldChange(query)

        // Then
        val updatedState = viewModel.state.first { it.query == query }
        assertEquals(query, updatedState.query)
        assertEquals(true, updatedState.showLoading)
    }

    @Test
    fun `queryFieldChange should update query state and not show loading`() = runTest {
        // Given
        val query = "q"

        // When
        viewModel.queryFieldChange(query)

        // Then
        val updatedState = viewModel.state.first { it.query == query }
        assertEquals(query, updatedState.query)
        assertEquals(false, updatedState.showLoading)
    }

    @Test
    fun `search should call getCountriesByNameUseCase when query is valid`() = runTest {
        // Given
        val query = "peru"
        coEvery { getCountriesByNameUseCase.invoke(query) } returns flow { emit(countryList) }

        // When
        viewModel.queryFieldChange(query)

        // Assert
        val updatedState = viewModel.state.first { it.countriesByName.isNotEmpty() }
        assertEquals(countryList, updatedState.countriesByName)
        coVerify(exactly = 1) { getCountriesByNameUseCase(query) }
    }
}