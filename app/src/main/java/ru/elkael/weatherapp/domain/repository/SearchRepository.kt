package ru.elkael.weatherapp.domain.repository

import ru.elkael.weatherapp.domain.entities.City
import ru.elkael.weatherapp.domain.entities.Forecast
import ru.elkael.weatherapp.domain.entities.Weather

interface SearchRepository {
    suspend fun searchCity(query: String): List<City>
}