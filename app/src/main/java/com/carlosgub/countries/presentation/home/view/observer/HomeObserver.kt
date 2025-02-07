package com.carlosgub.countries.presentation.home.view.observer

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.carlosgub.countries.presentation.home.viewmodel.HomeViewModel


@Composable
internal fun HomeObserver(viewModel: HomeViewModel){
    val searchState by viewModel.searchState.collectAsStateWithLifecycle()
    viewModel.setSearchList(searchState)
}