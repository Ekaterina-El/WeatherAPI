package ru.elkael.weatherapp.domain.useCase

import ru.elkael.weatherapp.domain.repository.FavoriteRepository
import javax.inject.Inject

class GetFavoriteCitiesUseCase @Inject constructor(private val repository: FavoriteRepository) {
    fun invoke() = repository.favoriteCities
}