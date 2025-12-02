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
}