package com.example.weatherforecastsapi.ui.weather

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherforecastsapi.data.WeatherDao
import com.example.weatherforecastsapi.models.CityWeather
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject
import java.io.IOException

data class WeatherState(
    val weather: CityWeather? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)

class WeatherViewModel(private val weatherDao: WeatherDao) : ViewModel() {

    private val cityCoordinates: Map<String, Pair<Double, Double>> = mapOf(
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

    private val _currentCity = MutableStateFlow("Lisboa")

    val uiState: StateFlow<WeatherState> = _currentCity.flatMapLatest { city ->
        weatherDao.getWeatherFlow(city).map { weatherData ->
            WeatherState(
                weather = weatherData,
                isLoading = false,
                error = null
            )
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = WeatherState(isLoading = true)
    )

    init {
        preloadAllCities()
    }

    fun updateCity(cityName: String, lat: Double, lon: Double) {
        _currentCity.value = cityName
        fetchWeatherFromApi(cityName, lat, lon)
    }

    private fun preloadAllCities() {
        cityCoordinates.forEach { (name, coords) ->
            val (lat, lon) = coords
            fetchWeatherFromApi(name, lat, lon)
        }
    }

    private fun fetchWeatherFromApi(cityName: String, lat: Double, lon: Double) {
        val url = "https://api.open-meteo.com/v1/forecast?latitude=$lat&longitude=$lon&current_weather=true"
        val client = OkHttpClient()
        val request = Request.Builder().url(url).build()

        client.newCall(request).enqueue(object : okhttp3.Callback {
            override fun onFailure(call: okhttp3.Call, e: IOException) {
            }

            override fun onResponse(call: okhttp3.Call, response: okhttp3.Response) {
                response.use { res ->
                    if (!res.isSuccessful) {
                        return
                    }

                    val jsonString = res.body!!.string()
                    val jsonObject = JSONObject(jsonString)

                    if (jsonObject.has("current_weather")) {
                        val current = jsonObject.getJSONObject("current_weather")
                        val code = current.getInt("weathercode")
                        val description = getConditionDescription(code)
                        val weatherData = CityWeather(
                            cityName = cityName,
                            temperature = current.getDouble("temperature"),
                            windSpeed = current.getDouble("windspeed"),
                            latitude = lat,
                            longitude = lon,
                            weatherCode = code,
                            conditionDescription = description
                        )
                        viewModelScope.launch {
                            weatherDao.insertWeather(weatherData)
                        }
                    }
                }
            }
        })
    }

    private fun getConditionDescription(code: Int): String {
        return when (code) {
            0 -> "Céu limpo"
            1, 2 -> "Maioritariamente limpo"
            3 -> "Nublado"
            45, 48 -> "Nevoeiro"
            51, 53, 55 -> "Chuvisco"
            61, 63, 65 -> "Chuva"
            71, 73, 75 -> "Neve"
            80, 81, 82 -> "Aguaceiros"
            95, 96, 99 -> "Trovoada"
            else -> "Condição desconhecida"
        }
    }
}