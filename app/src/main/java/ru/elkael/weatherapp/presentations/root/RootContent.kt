package ru.elkael.weatherapp.presentations.root

import androidx.compose.runtime.Composable
import com.arkivanov.decompose.extensions.compose.jetpack.stack.Children
import ru.elkael.weatherapp.presentations.details.CityDetailsContent
import ru.elkael.weatherapp.presentations.favorite.FavoriteCityContent
import ru.elkael.weatherapp.presentations.search.SearchContent
import ru.elkael.weatherapp.presentations.ui.theme.WeatherAppTheme

@Composable
fun RootContent(component: RootComponent) {
    WeatherAppTheme {
        Children(stack = component.childStack) {
            when (val instance = it.instance) {
                is RootComponent.Child.CityDetail -> CityDetailsContent(instance.component)
                is RootComponent.Child.FavoriteCities -> FavoriteCityContent(instance.component)
                is RootComponent.Child.Search -> SearchContent(instance.component)
            }
        }
    }
}