package ru.elkael.weatherapp.domain.useCase

import ru.elkael.weatherapp.domain.repository.FavoriteRepository
import javax.inject.Inject

class RemoveFromFavoriteUseCase @Inject constructor(private val repository: FavoriteRepository) {
    suspend fun invoke(cityId: Int) = repository.removeFromFavorite(cityId)
}