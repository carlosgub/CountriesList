package com.carlosgub.countries.domain.usecase

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
            coEvery { repository.getAllCountries() } returns countryList

            // When
            val result = useCase("")

            // Then
            coVerify(exactly = 1) { repository.getAllCountries() }
            assertEquals(
                countryList.size,
                result.size,
            )
            assertEquals(countryList, result)
        }

    @Test
    fun `getCountriesByName should return a list of countries when query is pe`() =
        runBlocking {
            // Given
            coEvery { repository.getCountriesByName(any()) } returns countryList

            // When
            val result = useCase("PE")

            // Then
            coVerify(exactly = 1) { repository.getCountriesByName(any()) }
            assertEquals(countryList.size, result.size)
            assertEquals(countryList, result)
        }

    @Test
    fun `getAllCountries should return a empty list of countries`() =
        runBlocking {
            // Given
            val list = listOf<Country>()
            coEvery { repository.getAllCountries() } returns list

            // When
            val result = useCase("")

            // Then
            coVerify(exactly = 1) { repository.getAllCountries() }
            assertEquals(list.size, result.size)
            assertEquals(list, result)
        }
}
