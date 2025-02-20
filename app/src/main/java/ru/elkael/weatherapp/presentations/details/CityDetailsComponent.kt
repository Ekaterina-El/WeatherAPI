package ru.elkael.weatherapp.presentations.details

import kotlinx.coroutines.flow.Flow

interface CityDetailsComponent {
    val state: Flow<CityDetailsStore.State>

    fun onClickBack()
    fun onClickIsFavorite()
}