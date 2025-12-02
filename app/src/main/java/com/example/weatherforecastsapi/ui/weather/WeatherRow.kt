package com.example.weatherforecastsapi.ui.weather

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.weatherforecastsapi.models.CityWeather

@Composable
fun WeatherRow(weather: CityWeather) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFE3F2FD)) // Um azul claro
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Nome da Cidade
            Text(
                text = weather.cityName,
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Temperatura gigante
            Text(
                text = "${weather.temperature}º",
                fontSize = 64.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Color(0xFF1565C0) // Azul escuro
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Vento
            Text(
                text = "Vento: ${weather.windSpeed} km/h",
                fontSize = 18.sp
            )
        }
    }
}