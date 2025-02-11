package ru.elkael.weatherapp.data.mapper

import ru.elkael.weatherapp.data.network.dto.SearchCityDto
import ru.elkael.weatherapp.domain.entities.City

fun SearchCityDto.toEntity(): City = City(
    id = this.id,
    name = this.name,
    country = this.country
)

fun List<SearchCityDto>.toEntities(): List<City> = this.map { it.toEntity() }