package ru.elkael.weatherapp.presentations.details

import com.arkivanov.mvikotlin.core.store.Store
import ru.elkael.weatherapp.presentations.details.DetailsStore.Intent
import ru.elkael.weatherapp.presentations.details.DetailsStore.Label
import ru.elkael.weatherapp.presentations.details.DetailsStore.State

internal interface DetailsStore : Store<Intent, State, Label> {
    data class State(val todo: Unit)

    sealed interface Intent {

    }

    sealed interface Label {
    }
}
