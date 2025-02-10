package com.carlosgub.countries.presentation.home.view.observer

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.carlosgub.countries.domain.model.Country
import com.carlosgub.countries.presentation.home.view.content.HomeContent
import com.carlosgub.countries.presentation.home.viewmodel.HomeScreenState
import com.carlosgub.countries.presentation.home.viewmodel.HomeViewModel

@Composable
internal fun HomeObserver(
    homeViewModel: HomeViewModel,
    goToDetail: (Country) -> Unit
) {
    val state = homeViewModel.state.collectAsStateWithLifecycle().value
    when (state) {
        is HomeScreenState.Success -> HomeContent(
            state.countriesList,
            goToDetail
        )

        is HomeScreenState.Error -> HomeError(state.message)

        is HomeScreenState.Loading -> HomeLoading()
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

@Composable
private fun HomeError(message: String, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
    ) {
        Text(
            text = message,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
    }

}