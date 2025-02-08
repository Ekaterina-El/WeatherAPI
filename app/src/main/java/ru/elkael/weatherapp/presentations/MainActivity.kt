package ru.elkael.weatherapp.presentations

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.arkivanov.decompose.defaultComponentContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.elkael.weatherapp.data.network.api.ApiFactory
import ru.elkael.weatherapp.presentations.root.DefaultRootComponent
import ru.elkael.weatherapp.presentations.root.RootContent

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val apiService = ApiFactory.apiService

        CoroutineScope(Dispatchers.Main).launch {
            val a = apiService.searchCity("Lond")
            val b = apiService.loadWeatherCity("London")
            val c = apiService.loadForecastForCity("London")
        }

        enableEdgeToEdge()
        setContent {
            RootContent(DefaultRootComponent(defaultComponentContext()))
        }
    }
}