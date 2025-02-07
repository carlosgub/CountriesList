package com.carlosgub.countries.data.repository

import com.carlosgub.countries.core.sealed.GenericState
import com.carlosgub.countries.data.datasource.remote.CountriesRemoteDataSource
import com.carlosgub.countries.mock.countryList
import com.carlosgub.countries.mock.usa
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test

class CountriesRepositoryImplTest {
    private lateinit var repository: CountriesRepositoryImpl
    private val countriesRemoteDataSource: CountriesRemoteDataSource = mockk()

    @Before
    fun setUp() {
        repository = CountriesRepositoryImpl(countriesRemoteDataSource)
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
            coEvery { countriesRemoteDataSource.getAllCountries() } returns response

            // When
            val result = repository.getAllCountries()

            // Then
            coVerify(exactly = 1) { countriesRemoteDataSource.getAllCountries() }
            assertEquals(response, result)
        }

    @Test
    fun `getCountryByName should return a list of countries`() =
        runBlocking {
            // Given
            val response = GenericState.Success(listOf(usa))
            coEvery { countriesRemoteDataSource.getCountriesByName(any()) } returns response

            // When
            val result = repository.getCountriesByName("U")

            // Then
            coVerify(exactly = 1) { countriesRemoteDataSource.getCountriesByName("U") }
            assertEquals(response, result)
        }

    @Test
    fun `getCountriesByName should return an error`() =
        runBlocking {
            // Given
            val response = GenericState.Error("error")
            coEvery { countriesRemoteDataSource.getCountriesByName(any()) } returns response

            // When
            val result = repository.getCountriesByName("U")

            // Then
            coVerify(exactly = 1) { countriesRemoteDataSource.getCountriesByName("U") }
            assertEquals(response, result)
        }

    @Test
    fun `getAllCountries should return an error`() =
        runBlocking {
            // Given
            val response = GenericState.Error("error")
            coEvery { countriesRemoteDataSource.getAllCountries() } returns response

            // When
            val result = repository.getAllCountries()

            // Then
            coVerify(exactly = 1) { countriesRemoteDataSource.getAllCountries() }
            assertEquals(response, result)
        }
}
