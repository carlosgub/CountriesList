@file:OptIn(ExperimentalMaterial3Api::class)

package com.carlosgub.countries.presentation.home.view

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.carlosgub.countries.R
import com.carlosgub.countries.domain.model.Country
import com.carlosgub.countries.presentation.home.view.content.HomeContent
import com.carlosgub.countries.presentation.home.viewmodel.HomeViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
internal fun HomeScreen(goToDetail: (Country) -> Unit) {
    val viewModel = koinViewModel<HomeViewModel>()
    val state = viewModel.state.collectAsStateWithLifecycle().value
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { HomeTopBar() },
    ) { innerPadding ->
        HomeContent(
            state = state,
            modifier = Modifier.padding(innerPadding),
            onQueryChange = { query ->
                viewModel.queryFieldChange(query)
            },
            goToDetail = goToDetail,
        )
    }
}

@Composable
private fun HomeTopBar() {
    CenterAlignedTopAppBar(
        title = {
            Text(stringResource(R.string.home_screen_title))
        },
    )
}
