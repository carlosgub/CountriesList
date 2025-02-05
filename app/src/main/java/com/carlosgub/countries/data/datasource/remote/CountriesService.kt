package com.carlosgub.countries.data.datasource.remote

import com.carlosgub.countries.domain.model.Country
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface CountriesService {
    @GET("/v3.1/all")
    suspend fun getAllCountries(): Response<List<Country>>

    @GET("/v3.1/name/{query}")
    suspend fun getCountriesByName(@Path("query") query: String): Response<List<Country>>
}