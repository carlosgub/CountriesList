package com.carlosgub.countries.domain.usecase

import com.carlosgub.countries.core.sealed.GenericState
import com.carlosgub.countries.domain.model.Country
import com.carlosgub.countries.domain.repository.CountriesRepository
import com.carlosgub.countries.mock.countryList
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test

class GetCountriesListUseCaseTest {
    private lateinit var useCase: GetCountriesListUseCase
    private val repository: CountriesRepository = mockk()

    @Before
    fun setUp() {
        useCase = GetCountriesListUseCase(repository)
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `getAllCountries should return a list of countries`() =
        runBlocking {
            // Given
            val response = GenericState.Success(countryList)
            coEvery { repository.getAllCountries() } returns response

            // When
            val result = useCase("")

            // Then
            coVerify(exactly = 1) { repository.getAllCountries() }
            assertEquals(
                response.item.size,
                (result as GenericState.Success).item.size,
            )
            assertEquals(response, result)
        }

    @Test
    fun `getAllCountries should return a list of countries when query is blank spaces`() =
        runBlocking {
            // Given
            val response = GenericState.Success(countryList)
            coEvery { repository.getAllCountries() } returns response

            // When
            val result = useCase("    ")

            // Then
            coVerify(exactly = 1) { repository.getAllCountries() }
            assertEquals(
                response.item.size,
                (result as GenericState.Success).item.size,
            )
            assertEquals(response, result)
        }

    @Test
    fun `getCountriesByName should return a list of countries when query is pe`() =
        runBlocking {
            // Given
            val response = GenericState.Success(countryList)
            coEvery { repository.getCountriesByName(any()) } returns response

            // When
            val result = useCase("PE")

            // Then
            coVerify(exactly = 1) { repository.getCountriesByName(any()) }
            assertEquals(
                response.item.size,
                (result as GenericState.Success).item.size,
            )
            assertEquals(response, result)
        }

    @Test
    fun `getAllCountries should return a empty list of countries`() =
        runBlocking {
            // Given
            val response =  GenericState.Success(listOf<Country>())
            coEvery { repository.getAllCountries() } returns response

            // When
            val result = useCase("")

            // Then
            coVerify(exactly = 1) { repository.getAllCountries() }
            assertEquals(
                response.item.size,
                (result as GenericState.Success).item.size,
            )
            assertEquals(response, result)
        }

    @Test
    fun `getAllCountries should return a an error`() =
        runBlocking {
            // Given
            val response =  GenericState.Error("error")
            coEvery { repository.getAllCountries() } returns response

            // When
            val result = useCase("")

            // Then
            coVerify(exactly = 1) { repository.getAllCountries() }
            assertEquals(
                response,
                result,
            )
        }

    @Test
    fun `getCountriesByName should return a an error`() =
        runBlocking {
            // Given
            val response =  GenericState.Error("error")
            coEvery { repository.getCountriesByName(any()) } returns response

            // When
            val result = useCase("PE")

            // Then
            coVerify(exactly = 1) { repository.getCountriesByName(any()) }
            assertEquals(
                response,
                result,
            )
        }
}
