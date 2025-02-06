package com.carlosgub.countries.presentation.home.view.content

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.carlosgub.countries.R
import com.carlosgub.countries.domain.model.Country
import com.carlosgub.countries.presentation.home.view.HomeScreen
import com.carlosgub.countries.presentation.home.viewmodel.HomeScreenState


@Composable
internal fun HomeContent(
    state: HomeScreenState,
    onQueryChange: (String) -> Unit,
    goToDetail: (Country) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize(),
    ) {
        HomeSearch(
            query = state.query,
            onQueryChange = onQueryChange,
        )
        if (state.showLoading) {
            HomeLoading()
        } else {
            HomeCountriesListContent(
                state = state,
                goToDetail = goToDetail,
            )
        }
    }
}

@Composable
private fun HomeCountriesListContent(
    state: HomeScreenState,
    goToDetail: (Country) -> Unit,
) {
    if (state.query.length >= 2) {
        HomeQueryContent(
            countriesByName = state.countriesByName,
            goToDetail = goToDetail,
        )
    } else {
        HomeAllCountriesContent(
            allCountries = state.allCountries,
            goToDetail = goToDetail,
        )
    }
}

@Composable
private fun HomeAllCountriesContent(
    allCountries: List<Country>,
    goToDetail: (Country) -> Unit,
) {
    CountriesList(
        countries = allCountries,
        goToDetail = goToDetail,
    )
}

@Composable
private fun HomeQueryContent(
    countriesByName: List<Country>,
    goToDetail: (Country) -> Unit,
) {
    if (countriesByName.isNotEmpty()) {
        CountriesList(
            countries = countriesByName,
            goToDetail = goToDetail,
        )
    } else {
        Text(
            stringResource(R.string.home_screen_search_error_message),
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
        )
    }
}

@Composable
private fun HomeSearch(
    query: String,
    modifier: Modifier = Modifier,
    onQueryChange: (String) -> Unit,
) {
    val focusManager = LocalFocusManager.current
    OutlinedTextField(
        value = query,
        onValueChange = { newQuery ->
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

@Composable
private fun CountriesList(
    countries: List<Country>,
    modifier: Modifier = Modifier,
    goToDetail: (Country) -> Unit,
) {
    LazyColumn(modifier.padding(horizontal = 16.dp)) {
        items(countries) { country ->
            CountryItem(
                country = country,
                goToDetail = goToDetail,
            )
        }
    }
}

@Composable
private fun CountryItem(
    country: Country,
    modifier: Modifier = Modifier,
    goToDetail: (Country) -> Unit,
) {
    Card(
        colors = CardDefaults.cardColors().copy(
            containerColor = Color.White,
        ),
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 8.dp)
            .clickable {
                goToDetail(country)
            },
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(country.flags.png)
                    .crossfade(true)
                    .build(),
                contentDescription = null,
                modifier = Modifier.size(50.dp),
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 12.dp),
            ) {
                Text(text = country.name.common)
                Text(text = country.name.official)
                val capital = country.capital?.getOrNull(0)
                if (capital != null) {
                    Text(text = capital)
                }
            }
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

@Preview(showBackground = true)
@Composable
private fun HomeContentPreview(
    @PreviewParameter(HomeContentPreviewParameterProvider::class) state: HomeScreenState,
) {
    HomeContent(
        state = state,
        onQueryChange = {},
        goToDetail = {}
    )
}