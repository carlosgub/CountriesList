package com.carlosgub.countries.presentation.home.view.content

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.carlosgub.countries.domain.model.Country


@Composable
internal fun HomeContent(
    countries: List<Country>,
    goToDetail: (Country) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize(),
    ) {
        HomeCountriesListContent(
            countries = countries,
            goToDetail = goToDetail,
        )
    }
}

@Composable
private fun HomeCountriesListContent(
    countries: List<Country>,
    goToDetail: (Country) -> Unit,
) {
    CountriesList(
        countries = countries,
        goToDetail = goToDetail,
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

@Preview(showBackground = true)
@Composable
private fun HomeContentPreview(
    @PreviewParameter(HomeContentPreviewParameterProvider::class) countries: List<Country>,
) {
    HomeContent(
        countries = countries,
        goToDetail = {}
    )
}