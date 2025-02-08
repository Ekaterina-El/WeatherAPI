package ru.elkael.weatherapp.data.network.dto

import com.google.gson.annotations.SerializedName
import ru.elkael.weatherapp.domain.entities.Weather

data class WeatherForecastDto(
    @SerializedName("current") val currentWeather: Weather,
    @SerializedName("forecast") val forecast: ForecastDto,
)
