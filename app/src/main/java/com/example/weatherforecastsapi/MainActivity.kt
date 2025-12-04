package com.example.weatherforecastsapi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.weatherforecastsapi.data.AppDatabase
import com.example.weatherforecastsapi.ui.theme.WeatherForecastsAPITheme
import com.example.weatherforecastsapi.ui.weather.WeatherScreen
import com.example.weatherforecastsapi.ui.weather.WeatherViewModel
import com.example.weatherforecastsapi.ui.weather.WeatherViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val database = AppDatabase.getDatabase(applicationContext)
        val weatherDao = database.weatherDao()
        val viewModelFactory = WeatherViewModelFactory(weatherDao)

        setContent {
            WeatherForecastsAPITheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val navController = rememberNavController()
                    val weatherViewModel: WeatherViewModel = viewModel(factory = viewModelFactory)

                    NavHost(
                        navController = navController,
                        startDestination = "weather",
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable("weather") {
                            val uiState by weatherViewModel.uiState.collectAsState()
                            WeatherScreen(
                                uiState = uiState,
                                onUpdateCity = { cityName, lat, lon ->
                                    weatherViewModel.updateCity(cityName, lat, lon)
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}