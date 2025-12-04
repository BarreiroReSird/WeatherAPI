package com.example.weatherforecastsapi.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.weatherforecastsapi.models.CityWeather
import kotlinx.coroutines.flow.Flow

@Dao
interface WeatherDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWeather(weather: CityWeather)

    @Query("SELECT * FROM weather_table WHERE cityName = :cityName")
    fun getWeatherFlow(cityName: String): Flow<CityWeather?>
}