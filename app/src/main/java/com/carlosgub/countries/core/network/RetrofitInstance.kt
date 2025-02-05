package com.carlosgub.countries.core.network

import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory

object RetrofitInstance {
    private const val BASE_URL = "https://restcountries.com/"
    private val withUnknownKeys = Json { ignoreUnknownKeys = true }

    fun getInstance(): Retrofit {
        return Retrofit
            .Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(
                withUnknownKeys.asConverterFactory(
                    "application/json; charset=UTF8".toMediaType(),
                ),
            )
            .build()
    }
}
