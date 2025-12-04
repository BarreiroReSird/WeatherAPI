package com.example.weatherforecastsapi.ui.weather

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.weatherforecastsapi.data.WeatherDao

class WeatherViewModelFactory(private val weatherDao: WeatherDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WeatherViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return WeatherViewModel(weatherDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}