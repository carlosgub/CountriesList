package com.carlosgub.countries.data.repository

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

class CountriesRepositoryImplTest{
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
    fun `getAllCountries should return a list of countries`() = runBlocking {
        // Given
        coEvery { countriesRemoteDataSource.getAllCountries() } returns countryList

        // When
        val result = repository.getAllCountries()

        // Then
        coVerify(exactly = 1) { countriesRemoteDataSource.getAllCountries() }
        assertEquals(countryList, result)
    }

    @Test
    fun `getCountryByName should return a list of countries`() = runBlocking {
        // Given
        val mockCountries = listOf(usa)
        coEvery { countriesRemoteDataSource.getCountriesByName(any()) } returns mockCountries

        // When
        val result = repository.getCountriesByName("U")

        // Then
        coVerify(exactly = 1) { countriesRemoteDataSource.getCountriesByName("U") }
        assertEquals(mockCountries, result)

    }
}