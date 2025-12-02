package com.example.weatherforecastsapi.ui.weather

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.weatherforecastsapi.models.CityWeather
import okhttp3.*
import org.json.JSONObject
import java.io.IOException

// 1. Definimos os estados possíveis do nosso ecrã
data class WeatherState(
    val weather: CityWeather? = null, // Os dados do tempo (pode ser nulo no início)
    val isLoading: Boolean = false,   // Se estamos a carregar
    val error: String? = null         // Se houve erro
)

class WeatherViewModel : ViewModel() {

    // 2. Variável que a UI vai "observar". Quando isto muda, o ecrã redesenha-se.
    var uiState = mutableStateOf(WeatherState())
        private set

    // 3. Função para ir buscar o tempo à Open-Meteo
    fun fetchWeather(cityName: String, lat: Double, lon: Double) {
        // Antes de começar, dizemos à UI que estamos a carregar
        uiState.value = uiState.value.copy(isLoading = true, error = null)

        val client = OkHttpClient()
        // URL da API Open-Meteo (Gratuita)
        val url = "https://api.open-meteo.com/v1/forecast?latitude=$lat&longitude=$lon&current_weather=true"

        val request = Request.Builder().url(url).build()

        // Fazemos a chamada assíncrona (em background)
        client.newCall(request).enqueue(object : Callback {

            // Caso falhe (sem internet, etc)
            override fun onFailure(call: Call, e: IOException) {
                uiState.value = uiState.value.copy(
                    isLoading = false,
                    error = e.message
                )
            }

            // Caso tenhamos resposta do servidor
            override fun onResponse(call: Call, response: Response) {
                response.use {
                    if (!response.isSuccessful) {
                        uiState.value = uiState.value.copy(isLoading = false, error = "Erro API")
                        return
                    }

                    // Ler o JSON recebido
                    val jsonString = response.body!!.string()
                    val jsonObject = JSONObject(jsonString)

                    // A API devolve um objeto chamado "current_weather"
                    if (jsonObject.has("current_weather")) {
                        val current = jsonObject.getJSONObject("current_weather")
                        val temp = current.getDouble("temperature")
                        val wind = current.getDouble("windspeed")

                        // Criar o nosso objeto CityWeather
                        val weatherData = CityWeather(
                            cityName = cityName,
                            temperature = temp,
                            windSpeed = wind,
                            latitude = lat,
                            longitude = lon
                        )

                        // Atualizar a UI com os dados novos
                        uiState.value = uiState.value.copy(
                            isLoading = false,
                            weather = weatherData
                        )
                    }
                }
            }
        })
    }

    // Função que recebe apenas o nome da cidade e escolhe as coordenadas
    fun fetchWeather(cityName: String) {
        val city = cityName.trim()

        val (lat, lon) = when (city.lowercase()) {
            "viana do castelo" -> 41.69 to -8.83
            "braga" -> 41.55 to -8.42
            "vila real" -> 41.30 to -7.74
            "bragança", "braganca" -> 41.80 to -6.76
            "porto" -> 41.15 to -8.61
            "aveiro" -> 40.64 to -8.65
            "viseu" -> 40.66 to -7.91
            "guarda" -> 40.54 to -7.27
            "coimbra" -> 40.21 to -8.43
            "castelo branco" -> 39.82 to -7.49
            "leiria" -> 39.74 to -8.81
            "santarém", "santarem" -> 39.23 to -8.68
            "lisboa", "lisbon" -> 38.71 to -9.14
            "setúbal", "setubal" -> 38.52 to -8.89
            "portalegre" -> 39.29 to -7.43
            "évora", "evora" -> 38.57 to -7.91
            "beja" -> 38.01 to -7.86
            "faro" -> 37.02 to -7.93
            "funchal" -> 32.65 to -16.91
            "ponta delgada" -> 37.74 to -25.67
            else -> {
                uiState.value = uiState.value.copy(
                    isLoading = false,
                    error = "Cidade desconhecida: $city"
                )
                return
            }
        }

        fetchWeather(city, lat, lon)
    }
}