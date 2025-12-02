package com.example.weatherforecastsapi.ui.weather

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun WeatherScreen() {
    // 1. Obter o ViewModel
    val viewModel: WeatherViewModel = viewModel()
    // 2. Ler o estado atual
    val uiState by viewModel.uiState

    // 2.1 Cidade selecionada pelo utilizador (capitais de distrito)
    val cities = listOf(
        "Viana do Castelo",
        "Braga",
        "Vila Real",
        "Bragança",
        "Porto",
        "Aveiro",
        "Viseu",
        "Guarda",
        "Coimbra",
        "Castelo Branco",
        "Leiria",
        "Santarém",
        "Lisboa",
        "Setúbal",
        "Portalegre",
        "Évora",
        "Beja",
        "Faro",
        "Funchal",
        "Ponta Delgada"
    )
    var selectedCity = remember { mutableStateOf(cities.first()) }
    var expanded = remember { mutableStateOf(false) }

    // 3. Assim que o ecrã abre, pedir os dados da cidade inicial
    LaunchedEffect(Unit) {
        viewModel.fetchWeather(selectedCity.value)
    }

    // 4. Desenhar o ecrã consoante o estado
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.TopCenter
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
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

            Spacer(modifier = Modifier.height(24.dp))

            Button(onClick = { expanded.value = true }) {
                Text(text = "Cidade: ${selectedCity.value}")
            }

            DropdownMenu(
                expanded = expanded.value,
                onDismissRequest = { expanded.value = false }
            ) {
                cities.forEach { city ->
                    DropdownMenuItem(
                        text = { Text(text = city) },
                        onClick = {
                            selectedCity.value = city
                            expanded.value = false
                            viewModel.fetchWeather(city)
                        }
                    )
                }
            }
        }
    }
}