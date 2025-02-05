package com.carlosgub.countries.presentation.home

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.carlosgub.countries.presentation.detail.DetailActivity
import com.carlosgub.countries.presentation.home.view.HomeScreen
import com.carlosgub.countries.ui.theme.CountriesTheme

class HomeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CountriesTheme {
                HomeScreen { country ->
                    startActivity(DetailActivity.newIntent(this@HomeActivity, country))
                }
            }
        }
    }
}
