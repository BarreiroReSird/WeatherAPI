package com.example.weatherforecastsapi.models

data class CityWeather(
    var cityName: String = "",
    var temperature: Double = 0.0,
    var windSpeed: Double = 0.0,
    var latitude: Double = 0.0,
    var longitude: Double = 0.0
)