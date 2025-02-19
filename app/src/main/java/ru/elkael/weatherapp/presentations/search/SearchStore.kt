package ru.elkael.weatherapp.presentations.search

import com.arkivanov.mvikotlin.core.store.Store
import ru.elkael.weatherapp.domain.entities.City
import ru.elkael.weatherapp.presentations.search.SearchStore.Intent
import ru.elkael.weatherapp.presentations.search.SearchStore.Label
import ru.elkael.weatherapp.presentations.search.SearchStore.State

interface SearchStore : Store<Intent, State, Label> {

    sealed interface Intent {
        data class OnChangeSearchText(val value: String): Intent
        data object ClickBack: Intent
        data object ClickSearch: Intent
        data class ClickOnCity(val city: City): Intent
    }

    data class State(
        var searchText: String,
        var state: SearchState,
        var reason: OpenReason
    ) {
        companion object {
            sealed interface SearchState {
                data object Initialized: SearchState
                data object Loading: SearchState
                data object LoadingError: SearchState
                data object EmptyResult: SearchState
                data class SuccessLoaded(val cities: List<City>): SearchState
            }
        }
    }

    sealed interface Label {
        data object CityAddedToFavorites: Label
        data class ShowCityWeather(val city: City): Label
        data object GoBack: Label
    }
}
