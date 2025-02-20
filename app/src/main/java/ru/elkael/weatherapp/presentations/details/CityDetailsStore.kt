package ru.elkael.weatherapp.presentations.details

import com.arkivanov.mvikotlin.core.store.Store
import ru.elkael.weatherapp.domain.entities.City
import ru.elkael.weatherapp.domain.entities.Weather
import ru.elkael.weatherapp.presentations.details.CityDetailsStore.Intent
import ru.elkael.weatherapp.presentations.details.CityDetailsStore.Label
import ru.elkael.weatherapp.presentations.details.CityDetailsStore.State

interface CityDetailsStore : Store<Intent, State, Label> {
    data class State(
        val city: City,
        val isFavorite: Boolean,
        val forecastState: ForecastState,
    ) {
        sealed interface ForecastState {
            data object Initial: ForecastState
            data object Loading: ForecastState
            data object LoadingError: ForecastState
            data class Loaded(
                val currentWeather: Weather,
                val upcomingWeather: List<Weather>
            ): ForecastState
        }
    }

    sealed interface Intent {
        data object ClickBack: Intent
        data object ClickFavorite: Intent
    }

    sealed interface Label {
        data object ClickBack: Label
    }
}
