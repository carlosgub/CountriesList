@file:OptIn(ExperimentalCoroutinesApi::class)

package com.carlosgub.countries.presentation.home.viewmodel

import com.carlosgub.countries.core.sealed.GenericState
import com.carlosgub.countries.domain.usecase.GetCountriesListUseCase
import com.carlosgub.countries.mock.countryList
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceTimeBy
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
        coEvery {
            getCountriesListUseCase(query)
        } returns GenericState.Success(countryList)
        val stateFlowValues = mutableListOf<HomeScreenState>()
        val job = launch { viewModel.state.toList(stateFlowValues) }

        // When
        viewModel.queryFieldChange(query)
        advanceTimeBy(300L)
        runCurrent()

        // Verify
        assertEquals(HomeScreenState.Loading, stateFlowValues[0])
        assertEquals(HomeScreenState.Success(countryList), stateFlowValues[1])
        job.cancel()
        coVerify(exactly = 1) { getCountriesListUseCase(query) }
    }

    @Test
    fun `queryFieldChange should emit Loading then Error`() = runTest {
        // Given
        val query = "USA"
        val response = GenericState.Error("error")

        coEvery {
            getCountriesListUseCase(query)
        } returns response

        val stateFlowValues = mutableListOf<HomeScreenState>()
        val job = launch { viewModel.state.toList(stateFlowValues) }

        // When
        viewModel.queryFieldChange(query)
        advanceTimeBy(300L)
        runCurrent()

        // Verify
        assertEquals(HomeScreenState.Loading, stateFlowValues[0])
        assertEquals(
            response.message,
            (stateFlowValues[1] as HomeScreenState.Error).message
        )

        job.cancel()
        coVerify(exactly = 1) { getCountriesListUseCase(query) }
    }
}
