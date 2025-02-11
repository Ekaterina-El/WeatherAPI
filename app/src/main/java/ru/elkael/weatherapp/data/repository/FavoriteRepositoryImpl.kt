package ru.elkael.weatherapp.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.elkael.weatherapp.data.local.dao.FavoriteCitiesDao
import ru.elkael.weatherapp.data.mapper.toDbModel
import ru.elkael.weatherapp.data.mapper.toEntities
import ru.elkael.weatherapp.domain.entities.City
import ru.elkael.weatherapp.domain.repository.FavoriteRepository
import javax.inject.Inject

class FavoriteRepositoryImpl @Inject constructor(private val dao: FavoriteCitiesDao) : FavoriteRepository {
    override val favoriteCities: Flow<List<City>> get() = dao.getFavoriteCities().map { it.toEntities() }

    override fun observeIsFavorite(cityId: Int): Flow<Boolean> = dao.observeIsFavourite(cityId)

    override suspend fun addToFavorite(city: City) = dao.addFavoriteCity(city.toDbModel())

    override suspend fun removeFromFavorite(cityId: Int) = dao.removeFavoriteCity(cityId)
}