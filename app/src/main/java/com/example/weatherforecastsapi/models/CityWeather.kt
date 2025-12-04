package com.example.weatherforecastsapi.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "weather_table")
data class CityWeather(
    @PrimaryKey val cityName: String,
    val temperature: Double,
    val windSpeed: Double,
    val latitude: Double,
    val longitude: Double,
    val lastUpdated: Long = System.currentTimeMillis()
)