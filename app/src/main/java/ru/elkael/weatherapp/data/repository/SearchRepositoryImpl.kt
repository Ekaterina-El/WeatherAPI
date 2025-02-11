package ru.elkael.weatherapp.data.repository

import ru.elkael.weatherapp.data.mapper.toEntities
import ru.elkael.weatherapp.data.network.api.ApiService
import ru.elkael.weatherapp.domain.entities.City
import ru.elkael.weatherapp.domain.repository.SearchRepository
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(private val apiService: ApiService) : SearchRepository {
    override suspend fun searchCity(query: String): List<City> {
        return apiService.searchCity(query).toEntities()
    }
}