package ru.elkael.weatherapp.presentations.favorite

import com.arkivanov.mvikotlin.core.store.Store
import ru.elkael.weatherapp.presentations.favorite.FavoriteStore.Intent
import ru.elkael.weatherapp.presentations.favorite.FavoriteStore.Label
import ru.elkael.weatherapp.presentations.favorite.FavoriteStore.State

internal interface FavoriteStore : Store<Intent, State, Label> {

    sealed interface Intent {
    }

    data class State(val todo: Unit)

    sealed interface Label {
    }
}
