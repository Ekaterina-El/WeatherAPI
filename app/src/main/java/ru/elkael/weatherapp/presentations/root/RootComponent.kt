package ru.elkael.weatherapp.presentations.root

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import ru.elkael.weatherapp.presentations.details.CityDetailsComponent
import ru.elkael.weatherapp.presentations.favorite.FavoriteCitiesComponent
import ru.elkael.weatherapp.presentations.search.SearchComponent

interface RootComponent {
    val childStack: Value<ChildStack<*, Child>>

    sealed interface Child {
        data class FavoriteCities(val component: FavoriteCitiesComponent): Child
        data class CityDetail(val component: CityDetailsComponent): Child
        data class Search(val component: SearchComponent): Child
    }
}