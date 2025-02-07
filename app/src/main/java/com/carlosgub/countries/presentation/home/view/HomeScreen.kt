@file:OptIn(ExperimentalMaterial3Api::class)

package com.carlosgub.countries.presentation.home.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.carlosgub.countries.R
import com.carlosgub.countries.domain.model.Country
import com.carlosgub.countries.presentation.home.view.observer.HomeObserver
import com.carlosgub.countries.presentation.home.viewmodel.HomeViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
internal fun HomeScreen(goToDetail: (Country) -> Unit) {
    val viewModel = koinViewModel<HomeViewModel>()
    LaunchedEffect(Unit) {
        viewModel.queryFieldChange("")
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { HomeTopBar() },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
        ) {
            HomeSearch(
                onQueryChange = {
                    viewModel.queryFieldChange(it)
                },
            )
            HomeObserver(
                homeViewModel = viewModel,
                goToDetail = goToDetail
            )
        }

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

@Composable
private fun HomeSearch(
    modifier: Modifier = Modifier,
    onQueryChange: (String) -> Unit,
) {
    val focusManager = LocalFocusManager.current
    val query = remember { mutableStateOf("") }
    OutlinedTextField(
        value = query.value,
        onValueChange = { newQuery ->
            query.value = newQuery
            onQueryChange(newQuery)
        },
        label = { Text(stringResource(R.string.home_screen_search_placeholder)) },
        placeholder = { Text(stringResource(R.string.home_screen_text_field_placeholder)) },
        keyboardActions = KeyboardActions(
            onSearch = {
                focusManager.clearFocus(force = true)
            },
        ),
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = ImeAction.Search,
        ),
        singleLine = true,
        modifier = modifier
            .padding(16.dp)
            .fillMaxWidth(),
    )
}
