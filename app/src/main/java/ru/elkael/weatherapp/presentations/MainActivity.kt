package ru.elkael.weatherapp.presentations

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.arkivanov.decompose.defaultComponentContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.elkael.weatherapp.domain.useCase.AddToFavoriteUseCase
import ru.elkael.weatherapp.domain.useCase.SearchCityUseCase
import ru.elkael.weatherapp.presentations.root.DefaultRootComponent
import ru.elkael.weatherapp.presentations.root.RootContent
import javax.inject.Inject

class MainActivity : ComponentActivity() {
    @Inject
    lateinit var defaultRootComponentFactory: DefaultRootComponent.Factory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        (this.application as WeatherApp).applicationComponent.inject(this)
        val root = defaultRootComponentFactory.create(defaultComponentContext())
        
        enableEdgeToEdge()
        setContent {
            RootContent(root)
        }
    }
}