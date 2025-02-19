package ru.elkael.weatherapp.domain.useCase

import ru.elkael.weatherapp.domain.repository.SearchRepository
import javax.inject.Inject

class SearchCityUseCase @Inject constructor(private val repository: SearchRepository) {
    suspend operator fun invoke(query: String) = repository.searchCity(query)
}