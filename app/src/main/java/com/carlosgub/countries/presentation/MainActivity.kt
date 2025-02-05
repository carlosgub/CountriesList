package com.carlosgub.countries.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.carlosgub.countries.presentation.views.home.HomeScreen
import com.carlosgub.countries.ui.theme.CountriesTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CountriesTheme {
                HomeScreen()
            }
        }
    }
}
