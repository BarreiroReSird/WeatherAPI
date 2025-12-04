package com.example.weatherforecastsapi.ui.weather

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

val cities = mapOf(
    "Viana do Castelo" to (41.69 to -8.83),
    "Braga" to (41.55 to -8.42),
    "Vila Real" to (41.30 to -7.74),
    "Bragança" to (41.80 to -6.75),
    "Porto" to (41.15 to -8.61),
    "Aveiro" to (40.64 to -8.65),
    "Viseu" to (40.66 to -7.91),
    "Guarda" to (40.53 to -7.26),
    "Coimbra" to (40.21 to -8.41),
    "Castelo Branco" to (39.82 to -7.49),
    "Leiria" to (39.74 to -8.80),
    "Santarém" to (39.23 to -8.68),
    "Lisboa" to (38.71 to -9.14),
    "Setúbal" to (38.52 to -8.89),
    "Portalegre" to (39.29 to -7.43),
    "Évora" to (38.57 to -7.91),
    "Beja" to (38.01 to -7.86),
    "Faro" to (37.01 to -7.93),
    "Funchal" to (32.65 to -16.90),
    "Ponta Delgada" to (37.74 to -25.67)
)

@Composable
fun WeatherScreen(
    uiState: WeatherState,
    onUpdateCity: (String, Double, Double) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedCityName by remember { mutableStateOf("Lisboa") }

    LaunchedEffect(Unit) {
        val (lat, lon) = cities[selectedCityName]!!
        onUpdateCity(selectedCityName, lat, lon)
    }

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
                CircularProgressIndicator()
            } else if (uiState.error != null) {
                Text(text = "Ocorreu um erro: ${uiState.error}")
            } else {
                uiState.weather?.let { weatherData ->
                    WeatherRow(weather = weatherData)

                    val formattedDate = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
                        .format(Date(weatherData.lastUpdated))
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = "Última atualização online: $formattedDate")
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(onClick = { expanded = true }) {
                Text(text = "Cidade: $selectedCityName")
            }

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                cities.keys.forEach { city ->
                    DropdownMenuItem(
                        text = { Text(text = city) },
                        onClick = {
                            selectedCityName = city
                            expanded = false
                            val (lat, lon) = cities[city]!!
                            onUpdateCity(city, lat, lon)
                        }
                    )
                }
            }
        }
    }
}