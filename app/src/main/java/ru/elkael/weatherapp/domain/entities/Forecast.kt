package ru.elkael.weatherapp.domain.entities

data class Forecast(
    val currentWeather: Weather,
    val upcoming: List<Weather>
)