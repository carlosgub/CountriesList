package com.carlosgub.countries.domain.usecase

import com.carlosgub.countries.domain.model.Country
import com.carlosgub.countries.domain.repository.CountriesRepository
import com.carlosgub.countries.mock.countryList
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test

class GetCountriesByNameUseCaseTest {
    private lateinit var useCase: GetCountriesByNameUseCase
    private val repository: CountriesRepository = mockk()

    @Before
    fun setUp() {
        useCase = GetCountriesByNameUseCase(repository)
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `getCountriesByName should return a list of countries`() =
        runBlocking {
            // Given
            val query = "query"
            coEvery { repository.getCountriesByName(query) } returns countryList

            // When
            val result = useCase(query).first()

            // Then
            coVerify(exactly = 1) { repository.getCountriesByName(query) }
            assertEquals(countryList.size, result.size)
            assertEquals(countryList, result)
        }

    @Test
    fun `getCountriesByName should return a empty list of countries`() =
        runBlocking {
            // Given
            val query = "query"
            val list = listOf<Country>()
            coEvery { repository.getCountriesByName(query) } returns list

            // When
            val result = useCase(query).first()

            // Then
            coVerify(exactly = 1) { repository.getCountriesByName(query) }
            assertEquals(list.size, result.size)
            assertEquals(list, result)
        }
}
