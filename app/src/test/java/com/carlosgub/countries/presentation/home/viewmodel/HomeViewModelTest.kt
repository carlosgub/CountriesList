@file:OptIn(ExperimentalCoroutinesApi::class)

package com.carlosgub.countries.presentation.home.viewmodel

import com.carlosgub.countries.domain.usecase.GetCountriesListUseCase
import com.carlosgub.countries.mock.countryList
import com.carlosgub.countries.mock.usa
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class HomeViewModelTest {
    private lateinit var viewModel: HomeViewModel
    private val getCountriesListUseCase: GetCountriesListUseCase = mockk()
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        viewModel = HomeViewModel(getCountriesListUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `queryFieldChange should emit Loading then Success with results`() = runTest {
        // Given
        val query = "USA"

        coEvery { getCountriesListUseCase(query) } returns countryList

        // Capturar estados emitidos
        val stateFlowValues = mutableListOf<HomeScreenState>()
        val job = launch { viewModel.state.toList(stateFlowValues) }

        // When
        viewModel.queryFieldChange(query)

        // Avanzar el tiempo para que se complete el delay de 300ms
        advanceTimeBy(300L)

        // Esperar a que el estado final sea emitido
        runCurrent()

        // Verificar que se emitieron los estados esperados
        assertEquals(HomeScreenState.Loading, stateFlowValues[0]) // Primero Loading
        assertEquals(HomeScreenState.Success(countryList), stateFlowValues[1]) // Luego Success

        job.cancel()
        coVerify(exactly = 1) { getCountriesListUseCase(query) }
    }
}
