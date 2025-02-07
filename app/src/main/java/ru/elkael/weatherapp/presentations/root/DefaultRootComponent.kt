package ru.elkael.weatherapp.presentations.root

import com.arkivanov.decompose.ComponentContext

class DefaultRootComponent(componentContext: ComponentContext) : RootComponent, ComponentContext by componentContext