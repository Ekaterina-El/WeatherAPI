package ru.elkael.weatherapp.data.repository

import ru.elkael.weatherapp.data.mapper.toEntity
import ru.elkael.weatherapp.data.network.api.ApiService
import ru.elkael.weatherapp.domain.entities.Forecast
import ru.elkael.weatherapp.domain.entities.Weather
import ru.elkael.weatherapp.domain.repository.WeatherRepository
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(private val apiService: ApiService) : WeatherRepository {

    override suspend fun getWeather(cityId: Int): Weather {
        return apiService.loadWeatherCity(cityId.toCityQuery()).weather.toEntity()
    }

    override suspend fun getForecast(cityId: Int): Forecast {
        return apiService.loadForecastForCity(cityId.toCityQuery()).toEntity()
    }

    companion object {
        private const val CITY_ID_PREFIX = "id:"

        private fun Int.toCityQuery() = CITY_ID_PREFIX + this
    }
}
