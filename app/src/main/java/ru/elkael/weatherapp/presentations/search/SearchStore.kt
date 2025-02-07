package ru.elkael.weatherapp.presentations.search

import com.arkivanov.mvikotlin.core.store.Store
import ru.elkael.weatherapp.presentations.search.SearchStore.Intent
import ru.elkael.weatherapp.presentations.search.SearchStore.Label
import ru.elkael.weatherapp.presentations.search.SearchStore.State

internal interface SearchStore : Store<Intent, State, Label> {

    sealed interface Intent {
    }

    data class State(val todo: Unit)

    sealed interface Label {
    }
}
