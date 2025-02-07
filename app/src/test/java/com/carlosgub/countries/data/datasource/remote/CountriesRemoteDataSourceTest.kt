package com.carlosgub.countries.data.datasource.remote

import com.carlosgub.countries.core.sealed.GenericState
import com.carlosgub.countries.mock.countryList
import com.carlosgub.countries.mock.usa
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Response
import retrofit2.Retrofit

class CountriesRemoteDataSourceTest {
    private lateinit var remoteDataSource: CountriesRemoteDataSource
    private val retrofit: Retrofit = mockk()
    private val mockService: CountriesService = mockk()

    @Before
    fun setUp() {
        coEvery { retrofit.create(CountriesService::class.java) } returns mockService
        remoteDataSource = CountriesRemoteDataSource(retrofit)
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `getAllCountries should return a list of countries`() =
        runBlocking {
            // Given
            coEvery { mockService.getAllCountries() } returns Response.success(countryList)

            // When
            val result = remoteDataSource.getAllCountries()

            // Then
            coVerify(exactly = 1) { mockService.getAllCountries() }
            assertEquals(
                GenericState.Success(countryList),
                result
            )
        }

    @Test
    fun `getAllCountries should return error state when response is null`() =
        runBlocking {
            // Given
            coEvery {
                mockService.getAllCountries()
            } returns Response.error(
                404,
                "error".toResponseBody("application/json".toMediaType()),
            )

            // When
            val result = remoteDataSource.getAllCountries()

            // Then
            coVerify(exactly = 1) { mockService.getAllCountries() }
            assertEquals(GenericState.Error("We have a problem"), result)
        }

    @Test
    fun `getAllCountries should return error when body is null`() =
        runBlocking {
            // Given
            coEvery {
                mockService.getAllCountries()
            } returns Response.success(null)

            // When
            val result = remoteDataSource.getAllCountries()

            // Then
            coVerify(exactly = 1) { mockService.getAllCountries() }
            assertEquals(
                GenericState.Error("null"),
                result
            )
        }

    @Test
    fun `getCountryByName should return a list of countries`() =
        runBlocking {
            // Given
            val mockCountries = listOf(usa)
            coEvery { mockService.getCountriesByName(any()) } returns Response.success(mockCountries)

            // When
            val result = remoteDataSource.getCountriesByName("U")

            // Then
            coVerify(exactly = 1) { mockService.getCountriesByName("U") }
            assertEquals(
                GenericState.Success(mockCountries),
                result
            )
        }

    @Test
    fun `getCountryByName should return error state when API fails`() =
        runBlocking {
            // Given
            coEvery {
                mockService.getCountriesByName("U")
            } returns Response.error(
                404,
                "error".toResponseBody("application/json".toMediaType()),
            )

            // When
            val result = remoteDataSource.getCountriesByName("U")

            // Then
            coVerify(exactly = 1) { mockService.getCountriesByName("U") }
            assertEquals(
                GenericState.Error("We have a problem"),
                result
            )
        }

    @Test
    fun `getCountryByName should return error when body is null`() =
        runBlocking {
            // Given
            coEvery {
                mockService.getCountriesByName("U")
            } returns Response.success(null)

            // When
            val result = remoteDataSource.getCountriesByName("U")

            // Then
            coVerify(exactly = 1) { mockService.getCountriesByName("U") }
            assertEquals(
                GenericState.Error("null"),
                result
            )
        }
}
