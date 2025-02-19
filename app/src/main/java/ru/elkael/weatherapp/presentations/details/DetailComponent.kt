package ru.elkael.weatherapp.presentations.details

import kotlinx.coroutines.flow.Flow

interface DetailComponent {
    val state: Flow<DetailsStore.State>

    fun onClickBack()
    fun onClickIsFavorite()
}