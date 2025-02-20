package ru.elkael.weatherapp.presentations.favorite

import kotlinx.coroutines.flow.Flow
import ru.elkael.weatherapp.domain.entities.City

interface FavoriteCitiesComponent {

    val state: Flow<FavoriteCitiesStore.State>

    fun onClickFavoriteCity(city: City)
    fun onClickAddCity()
    fun onClickSearch()
}