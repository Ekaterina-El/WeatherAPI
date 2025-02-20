package ru.elkael.weatherapp.presentations.root

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.push
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.parcelable.Parcelable
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.parcelize.Parcelize
import ru.elkael.weatherapp.domain.entities.City
import ru.elkael.weatherapp.presentations.details.CityDefaultsDetailsComponent
import ru.elkael.weatherapp.presentations.favorite.DefaultFavoriteCityComponent
import ru.elkael.weatherapp.presentations.search.DefaultSearchComponent
import ru.elkael.weatherapp.presentations.search.OpenReason

class DefaultRootComponent @AssistedInject constructor(
    private val detailComponentFactory: CityDefaultsDetailsComponent.Factory,
    private val searchComponentFactory: DefaultSearchComponent.Factory,
    private val favoriteCitiesComponentFactory: DefaultFavoriteCityComponent.Factory,
    @Assisted("componentContext") componentContext: ComponentContext
) : RootComponent, ComponentContext by componentContext {
    private val navigation = StackNavigation<Config>()
    override val childStack: Value<ChildStack<*, RootComponent.Child>>
        get() = childStack(
            source = navigation,
            initialConfiguration = Config.FavoriteCities,
            handleBackButton = true,
            childFactory = ::child
        )

    private fun child(
        config: Config,
        componentContext: ComponentContext,
    ): RootComponent.Child {
        return when (config) {
            Config.FavoriteCities -> RootComponent.Child.FavoriteCities(
                component = favoriteCitiesComponentFactory.create(
                    componentContext = componentContext,
                    onSearchClicked = {
                        navigation.push(Config.SearchCity(openReason = OpenReason.SHOW_CITY_WEATHER))
                    },

                    onAddFavoriteCityClicked = {
                        navigation.push(Config.SearchCity(openReason = OpenReason.ADD_FAVORITE))
                    },

                    onFavoriteCityClicked = { city -> navigation.push(Config.CityDetails(city)) }
                )
            )

            is Config.CityDetails -> RootComponent.Child.CityDetail(
                component = detailComponentFactory.create(
                    city = config.city,
                    componentContext = componentContext,
                    onBackClicked = navigation::pop
                )
            )

            is Config.SearchCity -> RootComponent.Child.Search(
                component = searchComponentFactory.create(
                    componentContext = componentContext,
                    openReason = config.openReason,
                    onBackClicked = navigation::pop,
                    onAddedCityToFavorite = navigation::pop,
                    onShowCityWeather = { city -> navigation.push(Config.CityDetails(city)) }
                )
            )
        }
    }

    sealed interface Config: Parcelable {
        @Parcelize
        data object FavoriteCities: Config

        @Parcelize
        data class SearchCity(val openReason: OpenReason): Config

        @Parcelize
        data class CityDetails(val city: City): Config
    }

    @AssistedFactory
    interface Factory {
        fun create(
            @Assisted("componentContext") componentContext: ComponentContext
        ): DefaultRootComponent
    }
}