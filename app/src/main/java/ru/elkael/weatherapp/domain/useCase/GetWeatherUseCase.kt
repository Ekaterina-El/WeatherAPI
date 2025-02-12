package ru.elkael.weatherapp.domain.useCase

import ru.elkael.weatherapp.domain.repository.WeatherRepository
import javax.inject.Inject

class GetWeatherUseCase @Inject constructor(private val repository: WeatherRepository) {
    suspend operator fun invoke(cityId: Int) = repository.getWeather(cityId)
}