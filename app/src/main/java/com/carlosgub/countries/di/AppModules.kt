package com.carlosgub.countries.di

import com.carlosgub.countries.core.network.RetrofitInstance
import com.carlosgub.countries.data.datasource.remote.CountriesRemoteDataSource
import com.carlosgub.countries.data.repository.CountriesRepositoryImpl
import com.carlosgub.countries.domain.repository.CountriesRepository
import com.carlosgub.countries.domain.usecase.GetAllCountriesUseCase
import com.carlosgub.countries.domain.usecase.GetCountriesByNameUseCase
import com.carlosgub.countries.presentation.home.viewmodel.HomeViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val appModules = module {
    single {
        RetrofitInstance.getInstance()
    }

    // region ViewModel
    viewModelOf(::HomeViewModel)
    // endregion

    // region UseCases
    factory {
        GetAllCountriesUseCase(
            countriesRepository = get(),
        )
    }

    factory {
        GetCountriesByNameUseCase(
            countriesRepository = get(),
        )
    }
    // endregion

    // region Repository
    factory<CountriesRepository> {
        CountriesRepositoryImpl(
            countriesRemoteDataSource = get(),
        )
    }
    // endregion

    // region DataSources
    factory {
        CountriesRemoteDataSource(
            retrofit = get(),
        )
    }
    // endregion
}
