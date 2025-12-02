package com.example.weatherforecastsapi.ui.weather

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun WeatherScreen(
    cityName: String,
    lat: Double,
    lon: Double
) {
    // 1. Obter o ViewModel
    val viewModel: WeatherViewModel = viewModel()
    // 2. Ler o estado atual
    val uiState by viewModel.uiState

    // 3. Assim que o ecrã abre (ou a cidade muda), pedir os dados à API
    LaunchedEffect(cityName) {
        viewModel.fetchWeather(cityName, lat, lon)
    }

    // 4. Desenhar o ecrã consoante o estado
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        if (uiState.isLoading) {
            CircularProgressIndicator() // Rodinha a carregar
        }
        else if (uiState.error != null) {
            Text(text = "Ocorreu um erro: ${uiState.error}")
        }
        else {
            // Se tivermos dados, mostramos o cartão que criámos no Passo 3
            uiState.weather?.let { weatherData ->
                WeatherRow(weather = weatherData)
            }
        }
    }
}