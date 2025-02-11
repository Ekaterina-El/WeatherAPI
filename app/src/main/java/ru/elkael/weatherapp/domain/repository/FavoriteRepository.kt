package ru.elkael.weatherapp.domain.repository

import kotlinx.coroutines.flow.Flow
import ru.elkael.weatherapp.domain.entities.City
import ru.elkael.weatherapp.domain.entities.Forecast
import ru.elkael.weatherapp.domain.entities.Weather

interface FavoriteRepository {
    val favoriteCities: Flow<List<City>>
    fun observeIsFavorite(cityId: Int): Flow<Boolean>
    suspend fun addToFavorite(city: City)
    suspend fun removeFromFavorite(cityId: Int)
}