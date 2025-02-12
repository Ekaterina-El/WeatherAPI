package ru.elkael.weatherapp.presentations.favorite

import com.arkivanov.mvikotlin.core.store.Store
import ru.elkael.weatherapp.domain.entities.City
import ru.elkael.weatherapp.presentations.favorite.FavoriteStore.Intent
import ru.elkael.weatherapp.presentations.favorite.FavoriteStore.Label
import ru.elkael.weatherapp.presentations.favorite.FavoriteStore.State

internal interface FavoriteStore : Store<Intent, State, Label> {

    sealed interface Intent {
        data object ClickSearch: Intent
        data object ClickToAddFavorite: Intent
        data class ClickToFavoriteItem(val city: City): Intent
    }

    data class State(
        val cities: List<CityItem>
    ) {

        data class CityItem(
            val city: City,
            val weatherState: WeatherState
        )

        sealed interface WeatherState {
            data object Initial: WeatherState
            data object Loading: WeatherState
            data object Error: WeatherState
            data class Loaded(
                val tempC: Int,
                val conditionUrl: String
            ): WeatherState
        }
    }

    sealed interface Label {
        data object ClickSearch: Label
        data object ClickToAddFavorite: Label
        data class ClickToFavoriteItem(val city: City): Label
    }
}
