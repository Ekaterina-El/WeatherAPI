package ru.elkael.weatherapp.presentations.details

import com.arkivanov.decompose.ComponentContext

class DefaultDetailComponent(componentContext: ComponentContext) : DetailComponent,
    ComponentContext by componentContext {

}