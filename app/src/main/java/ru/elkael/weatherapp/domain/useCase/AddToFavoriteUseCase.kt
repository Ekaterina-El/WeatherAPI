package ru.elkael.weatherapp.domain.useCase

import ru.elkael.weatherapp.domain.entities.City
import ru.elkael.weatherapp.domain.repository.FavoriteRepository
import javax.inject.Inject

class AddToFavoriteUseCase @Inject constructor(private val repository: FavoriteRepository) {
    suspend fun invoke(city: City) = repository.addToFavorite(city)
}