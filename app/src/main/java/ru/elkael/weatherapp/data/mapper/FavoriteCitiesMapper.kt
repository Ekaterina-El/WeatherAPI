package ru.elkael.weatherapp.data.mapper

import ru.elkael.weatherapp.data.local.model.FavoriteCityDBModel
import ru.elkael.weatherapp.domain.entities.City

fun City.toDbModel(): FavoriteCityDBModel = FavoriteCityDBModel(
    id = this.id,
    name = this.name,
    country = this.country
)

fun FavoriteCityDBModel.toEntities(): City = City(
    id = this.id,
    name = this.name,
    country = this.country
)

fun List<FavoriteCityDBModel>.toEntities(): List<City> = this.map { it.toEntities() }