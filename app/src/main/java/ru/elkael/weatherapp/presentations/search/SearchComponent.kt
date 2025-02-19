package ru.elkael.weatherapp.presentations.search

import kotlinx.coroutines.flow.Flow
import ru.elkael.weatherapp.domain.entities.City

interface SearchComponent {
    val state: Flow<SearchStore.State>

    fun onChangeSearchValue(value: String)
    fun onClickSearch()
    fun onClickBack()
    fun onSelectCity(city: City)
}