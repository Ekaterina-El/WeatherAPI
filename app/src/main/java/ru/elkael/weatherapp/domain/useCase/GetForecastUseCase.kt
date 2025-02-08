package ru.elkael.weatherapp.domain.useCase

import ru.elkael.weatherapp.domain.repository.WeatherRepository
import javax.inject.Inject

class GetForecastUseCase @Inject constructor(private val repository: WeatherRepository) {
    suspend fun invoke(cityId: Int) = repository.getForecast(cityId)
}