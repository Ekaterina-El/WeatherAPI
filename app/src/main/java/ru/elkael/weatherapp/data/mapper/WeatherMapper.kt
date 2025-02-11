package ru.elkael.weatherapp.data.mapper

import ru.elkael.weatherapp.data.network.dto.ForecastDayDto
import ru.elkael.weatherapp.data.network.dto.WeatherDto
import ru.elkael.weatherapp.data.network.dto.WeatherForecastDto
import ru.elkael.weatherapp.domain.entities.Forecast
import ru.elkael.weatherapp.domain.entities.Weather
import java.util.Calendar
import java.util.Date


fun WeatherForecastDto.toEntity() = Forecast(
    currentWeather = this.currentWeather.toEntity(),
    upcoming = this.forecast.upcoming.drop(1).toEntities()
)

fun List<ForecastDayDto>.toEntities(): List<Weather> = this.map { it.toEntity() }

fun ForecastDayDto.toEntity() = Weather(
    tempC = this.weather.tempC.toInt(),
    conditionText = this.weather.condition.text,
    conditionUrl = this.weather.condition.iconUrl.correctImgUrl(),
    date = this.date.toCalendar()
)

fun WeatherDto.toEntity() = Weather(
    tempC = this.tempC.toInt(),
    conditionText = this.condition.text,
    conditionUrl = this.condition.iconUrl.correctImgUrl(),
    date = this.lastUpdate.toCalendar()
)

private fun Long.toCalendar() = Calendar.getInstance().apply { time = Date(this@toCalendar * 1000L) }

private fun String.correctImgUrl() = "https:" + this.replace("64x64", "128x128")