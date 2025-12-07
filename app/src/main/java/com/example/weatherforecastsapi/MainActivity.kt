package com.example.weatherforecastsapi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.weatherforecastsapi.data.AppDatabase
import com.example.weatherforecastsapi.ui.AboutScreen
import com.example.weatherforecastsapi.ui.theme.WeatherForecastsAPITheme
import com.example.weatherforecastsapi.ui.weather.WeatherScreen
import com.example.weatherforecastsapi.ui.weather.WeatherViewModel
import com.example.weatherforecastsapi.ui.weather.WeatherViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val database = AppDatabase.getDatabase(applicationContext)
        val weatherDao = database.weatherDao()
        val viewModelFactory = WeatherViewModelFactory(weatherDao)

        setContent {
            WeatherForecastsAPITheme {
                val navController = rememberNavController()
                val weatherViewModel: WeatherViewModel = viewModel(factory = viewModelFactory)

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        WeatherBottomBar(navController = navController)
                    }
                ) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = "home",
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable("home") {
                            val uiState by weatherViewModel.uiState.collectAsState()
                            WeatherScreen(
                                uiState = uiState,
                                onUpdateCity = { cityName, lat, lon ->
                                    weatherViewModel.updateCity(cityName, lat, lon)
                                }
                            )
                        }

                        composable("about") {
                            AboutScreen()
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun WeatherBottomBar(navController: NavController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    BottomAppBar {
        NavigationBarItem(
            selected = currentRoute == "home",
            onClick = {
                navController.navigate("home") {
                    popUpTo("home") { saveState = true }
                    launchSingleTop = true
                    restoreState = true
                }
            },
            icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
            label = { Text("Meteo") }
        )

        NavigationBarItem(
            selected = currentRoute == "about",
            onClick = {
                navController.navigate("about") {
                    popUpTo("home") { saveState = true }
                    launchSingleTop = true
                    restoreState = true
                }
            },
            icon = { Icon(Icons.Default.Info, contentDescription = "Sobre") },
            label = { Text("Sobre") }
        )
    }
}