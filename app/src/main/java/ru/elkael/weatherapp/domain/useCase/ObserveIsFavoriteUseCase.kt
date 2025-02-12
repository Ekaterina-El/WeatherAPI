package ru.elkael.weatherapp.domain.useCase

import ru.elkael.weatherapp.domain.repository.FavoriteRepository
import javax.inject.Inject

class ObserveIsFavoriteUseCase @Inject constructor(private val repository: FavoriteRepository) {
    operator fun invoke(cityId: Int) = repository.observeIsFavorite(cityId)
}