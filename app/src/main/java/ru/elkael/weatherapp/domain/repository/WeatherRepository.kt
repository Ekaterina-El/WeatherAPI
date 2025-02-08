package ru.elkael.weatherapp.domain.repository

import ru.elkael.weatherapp.domain.entities.Forecast
import ru.elkael.weatherapp.domain.entities.Weather

interface WeatherRepository {
    suspend fun getWeather(cityId: Int): Weather
    suspend fun getForecast(cityId: Int): Forecast
}