package ru.elkael.weatherapp.presentations

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.arkivanov.decompose.defaultComponentContext
import ru.elkael.weatherapp.presentations.root.DefaultRootComponent
import ru.elkael.weatherapp.presentations.root.RootContent

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RootContent(DefaultRootComponent(defaultComponentContext()))
        }
    }
}