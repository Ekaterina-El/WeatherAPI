package ru.elkael.weatherapp.presentations.favorite

import kotlinx.coroutines.flow.Flow
import ru.elkael.weatherapp.domain.entities.City

interface FavoriteComponent {

    val state: Flow<FavoriteStore.State>

    fun onClickFavoriteCity(city: City)
    fun onClickAddCity()
    fun onClickSearch()
}