package com.carlosgub.countries.data.datasource.remote

import com.carlosgub.countries.core.sealed.GenericState
import com.carlosgub.countries.domain.model.Country
import retrofit2.Retrofit

internal class CountriesRemoteDataSource(
    private val retrofit: Retrofit,
) {
    suspend fun getAllCountries(): GenericState<List<Country>> {
        return try {
            val response = retrofit.create(CountriesService::class.java)
                .getAllCountries()
            if(response.isSuccessful){
                GenericState.Success(response.body()!!)
            }else{
                GenericState.Error("We have a problem")
            }
        } catch (ex: Exception) {
            GenericState.Error(ex.message.toString())
        }
    }

    suspend fun getCountriesByName(query: String): GenericState<List<Country>> {
        return try {
            val response = retrofit.create(CountriesService::class.java)
                .getCountriesByName(query)
            if(response.isSuccessful){
                GenericState.Success(response.body()!!)
            }else{
                GenericState.Error("We have a problem")
            }
        } catch (ex: Exception) {
            GenericState.Error(ex.message.toString())
        }

    }
}
