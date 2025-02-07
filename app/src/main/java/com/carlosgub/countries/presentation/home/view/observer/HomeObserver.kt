package com.carlosgub.countries.presentation.home.view.observer

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.carlosgub.countries.domain.model.Country
import com.carlosgub.countries.presentation.home.view.content.HomeContent
import com.carlosgub.countries.presentation.home.viewmodel.HomeScreenState
import com.carlosgub.countries.presentation.home.viewmodel.HomeViewModel

@Composable
internal fun HomeObserver(
    homeViewModel: HomeViewModel,
    goToDetail: (Country) -> Unit
) {
    val state = homeViewModel.state.collectAsState().value
    when (state) {
        is HomeScreenState.Success -> {
            HomeContent(
                state.countriesList,
                goToDetail
            )
        }

        is HomeScreenState.Error -> {

        }

        is HomeScreenState.Loading -> {
            HomeLoading()
        }
    }
}

@Composable
private fun HomeLoading(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        CircularProgressIndicator()
    }
}