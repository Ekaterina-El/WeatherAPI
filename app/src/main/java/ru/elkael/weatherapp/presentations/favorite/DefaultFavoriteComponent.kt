package ru.elkael.weatherapp.presentations.favorite

import com.arkivanov.decompose.ComponentContext

class DefaultFavoriteComponent(componentContext: ComponentContext) : FavoriteComponent,
    ComponentContext by componentContext {

}